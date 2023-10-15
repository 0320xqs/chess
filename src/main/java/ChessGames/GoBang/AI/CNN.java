package ChessGames.GoBang.AI;

import ChessGames.GoBang.GoBangChessPieces;
import ChessGames.GoBang.GoBangConfig;
import ChessGames.template.Model.Part;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import org.bytedeco.tesseract.ROW;
import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.conf.*;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.*;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

import static ChessGames.GoBang.GoBangConfig.*;


public class CNN {
    //    private static final Logger log = LoggerFactory.getLogger(LenetMnistExample.class);
    int channels = 1; // Number of input channels
    int outputNum = 225; // The number of possible outcomes
    int nEpochs = 1000; // Number of training epochs
    int seed = 123; //
    int batchSize = 64; // batch size
    double learningRate = 0.001;
//    GoBangConfig board;
    int [][] board;
    int ROWS;
    int COLS;
    int role, depth=4;

    public CNN(JSONObject json) {
        //获取参数
        this.board = new int[(int) json.get("COLS")][(int) json.get("ROWS")];
        ROWS = (int) json.get("ROWS");
        COLS = (int) json.get("COLS");
        JSONArray jsonArray = (JSONArray) json.get("board");
        int h = 0;
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                board[i][j] = (int) jsonArray.get(h);
                h++;
            }
        }
    }

    public void train() throws IOException {
        //加载数据
        DataSet dataSet = loadData();
        // 将数据集拆分为训练集和测试集
        Random random = new Random(12345);
        SplitTestAndTrain testAndTrain = dataSet.splitTestAndTrain(0.7); // 70% 的数据用于训练，30% 的数据用于测试
//        SplitTestAndTrain testAndTrain = dataSet.splitTestAndTrain(45000,random); // 70% 的数据用于训练，30% 的数据用于测试
        DataSetIterator trainIterator = new ListDataSetIterator<>(testAndTrain.getTrain().asList(), batchSize);
        DataSet train = testAndTrain.getTrain();
        DataSetIterator testIterator = new ListDataSetIterator<>(testAndTrain.getTest().asList(), batchSize);
        //加载模型
        MultiLayerNetwork model = getModel1();
        model.init();
        //接着训练
//        String modelFilePath = "model/weightThirdMyCnnModel151.zip";
//        MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork(modelFilePath);
//        model.setLearningRate(0.0001);
//        System.out.println("learningRate为："+model.getLearningRate(0));

        model.setListeners(new ScoreIterationListener(60)); //每60个打印一次分数
        double max = 0.0;
        // 训练模型
        for (int i = 0; i < nEpochs; i++) {
            while (trainIterator.hasNext()) {
                model.fit(trainIterator.next());
            }
            trainIterator.reset();

            // 训练集准确率
            Evaluation evaluation = new Evaluation(outputNum);
            while (trainIterator.hasNext()) {
                DataSet trainData = trainIterator.next();
                INDArray features = trainData.getFeatures();
                INDArray label = trainData.getLabels();
                INDArray predicted = model.output(features, false);
                evaluation.eval(label, predicted);
            }

            // 输出测试准确率
            double accuracy = evaluation.accuracy();

            trainIterator.reset();

            // 测试模型
            evaluation = new Evaluation(outputNum);
            while (testIterator.hasNext()) {
                DataSet testData = testIterator.next();
                INDArray features = testData.getFeatures();
                INDArray label = testData.getLabels();
                INDArray predicted = model.output(features, false);
                evaluation.eval(label, predicted);
            }
            // 输出测试准确率
            accuracy = evaluation.accuracy();

            testIterator.reset();
            if (accuracy > max) {
                max = accuracy;
                //存储模型
                try {
//                ModelSerializer.writeModel(model, new File("model/MyCnnModel"+i+".zip"), false);
                    ModelSerializer.writeModel(model, new File("model/weightFourthMyCnnModel" + i + ".zip"), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

//            System.out.println(evaluation.stats());
        }
    }

    public MultiLayerNetwork getModel1() {//正常CNN
//        配置CNN
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .l2(0.0005)
                .weightInit(WeightInit.XAVIER)
                .updater(new Nesterovs(learningRate, 0.9))
                .list()
                .layer(0, new ConvolutionLayer.Builder(5, 5)//卷积层
                        //nIn and nOut specify depth. nIn here is the nChannels and nOut is the number of filters to be applied
                        .nIn(channels)
                        .stride(1, 1)
                        /**
                         * nOut 等价于 我们在训练的时候需要使用多少个 filter
                         */
                        .nOut(64)
                        /**
                         * padding 对于个人用户来说，在使用中我们不需要自己去特别计算padding
                         * SAME -> ConvolutionMode.Same
                         * VALID -> ConvolutionMode.Truncate
                         */
                        .convolutionMode(ConvolutionMode.Same)//CNN的输出层和全连接层
                        .activation(Activation.RELU)
                        .build())
                .layer(1, new SubsamplingLayer.Builder(PoolingType.MAX)//子采样
                        .kernelSize(2, 2)
                        .stride(1, 1)
                        .build())
                .layer(2, new ConvolutionLayer.Builder(5, 5)//卷积层
                        //Note that nIn need not be specified in later layers
                        .stride(1, 1)
                        .nOut(128)
                        .activation(Activation.RELU)
                        .build())
                .layer(3, new SubsamplingLayer.Builder(PoolingType.MAX)//子采样层
                        .kernelSize(2, 2)
                        .stride(1, 1)
                        .build())
//                .layer(4,new DropoutLayer.Builder(0.5)
//                        .build())
                .layer(4, new DenseLayer.Builder().activation(Activation.RELU)//全连接层
                        .nOut(512).build())
                .layer(5, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)//输出层
                        .nOut(outputNum)
                        .activation(Activation.SOFTMAX)
                        .build())
                .setInputType(InputType.convolutionalFlat(ROWS, COLS, channels)) //See note below
                .build();
//                .backprop(true).pretrain(false).build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        return model;
    }

    public DataSet loadData() throws IOException {

        Random randNumGen = new Random(seed);// 训练数据的向量化
        //获取数据集合
//        String filePath = "E:/CNN_测试/board1.txt";
        String filePath = "E:/CNN_测试/board1 - 副本_左右上+中.txt";
        JSONObject jsonObject = readJsonFile(filePath);
        int dataSize = jsonObject.getJSONArray("list").size();//数据长度
        int count = 0;
        ArrayList<ArrayList<Integer>> listList = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> labelsList = new ArrayList<Integer>();
        ArrayList<Integer> weightList = new ArrayList<Integer>();
//        ArrayList<Integer> roleList = new ArrayList<Integer>();//先手1，后手-1
        int unRepCount = 1;
        int repCount = -1;
//        int weight = 0;
        //获取最大数量
        for (int i = 0; i < dataSize; i++) {//每一局五子棋
            JSONArray jsonArray = jsonObject.getJSONArray("list").getJSONObject(i).getJSONArray("board");
            for (int j = 0; j < jsonArray.size() - 2; j++) {//一条记录有这么多个可训练数据
                int k;
                //去重
                ArrayList<Integer> newlist = new ArrayList<Integer>();
                int weight = 0;
                boolean repflag = false;
                for (k = 0; k <= j; k++) {
                    newlist.add(Integer.parseInt(jsonArray.get(k).toString()));//新数据
                }
                if (listList.size() == 0) {//刚开始为空
                    //加权重
                    if (jsonArray.get(jsonArray.size() - 1).toString().equals("0")) {
//                            weight.add(new Float(1));
                        weight = 2;
                    } else if (jsonArray.get(jsonArray.size() - 1).toString().equals("1")) {
//                            weight.add(new Float(0.5));
                        weight = 1;
                    } else if (jsonArray.get(jsonArray.size() - 1).toString().equals("2")) {
//                            weight.add(new Float(2));
                        weight = 3;
                    }
//                        for (int l = 0; l < weight; l++) {
                    listList.add(newlist);//先放入一个
                    labelsList.add(Integer.parseInt(jsonArray.get(j + 1).toString()));
                    weightList.add(weight);
                    count++;
//                        }
                }
                for (int l = 0; l < listList.size(); l++) {
                    if (listList.get(l).equals(newlist)) {//判断是否分歧重复
                        repflag = true;
                        break;
                    }
                }
                if (!repflag) {
                    //加权重
                    if (jsonArray.get(jsonArray.size() - 1).toString().equals("0")) {
//                            weight.add(new Float(1));
                        weight = 2;
                    } else if (jsonArray.get(jsonArray.size() - 1).toString().equals("1")) {
                        if ((j + 1) % 2 == 0) {
//                                weight.add(new Float(2));
                            weight = 3;
                        } else {
//                                weight.add(new Float(0.5));
                            weight = 1;
                        }
                    } else if (jsonArray.get(jsonArray.size() - 1).toString().equals("2")) {
                        if ((j + 1) % 2 == 0) {
//                                weight.add(new Float(0.5));
                            weight = 1;
                        } else {
//                                weight.add(new Float(2));
                            weight = 3;
                        }
                    }
//                        for (int l = 0; l < weight; l++) {//加权（权重高的重复多些）
                    listList.add(newlist);
                    labelsList.add(Integer.parseInt(jsonArray.get(j + 1).toString()));
                    weightList.add(weight);
                    count++;
//                        }
                    unRepCount++;
                } else {
                    repCount++;
                }
            }
        }
        count = 0;
        for (int i = 0; i < listList.size(); i++) {
            for (int j = 0; j < weightList.get(i); j++) {//加权（权重高的重复多些）
                count++;
            }
        }
        //按输入形状创建一个输入数组
        INDArray input = Nd4j.zeros(count, channels, ROWS, COLS);
        //按输入形状i创建一个输出数组
        INDArray labels = Nd4j.zeros(count, outputNum);
        //处理数据
        count = 0;
        for (int i = 0; i < listList.size(); i++) {
            for (int j = 0; j < weightList.get(i); j++) {//加权（权重高的重复多些）
                for (int k = 0; k < listList.get(i).size(); k++) {
                    int x = listList.get(i).get(k) / COLS;
                    int y = listList.get(i).get(k) % ROWS;
                    input.putScalar(count, 0, x, y, (k + 1) % 2 == 1 ? 1 : -1);
                }
                labels.putScalar(count, labelsList.get(i), 1);
                count++;
            }
        }

        //存储处理后的数据
        JSONObject unRepData = new JSONObject();
        JSONObject obj = new JSONObject();
        obj.put("board", listList.get(0));
        ArrayList<JSONObject> jsonObjects = new ArrayList<>();
        jsonObjects.add(obj);
        unRepData.put("list", jsonObjects);
        for (int i = 1; i < listList.size(); i++) {
            obj = new JSONObject();
            obj.put("board", listList.get(i));
            unRepData.getJSONArray("list").add(obj);
        }
        filePath = "E:/CNN_测试/board1 - 副本_左右上+中_unRepData_sec.txt";
        String jsonString = JSON.toJSONString(unRepData);
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(jsonString);
        writer.close();

        //加权重
//        INDArray weightArray = Nd4j.create(weight);
        DataSet dataSet = new DataSet(input, labels);
        return dataSet;
    }

    /**
     * @param features
     * @return
     * @description 预测下一步
     */
    public int predict(INDArray features, ArrayList<Integer> boardState) throws IOException {
        //加载数据
//        DataSet dataSet = loadData();
        // 将数据集拆分为训练集和测试集
//        Random random = new Random(12345);
//        SplitTestAndTrain testAndTrain = dataSet.splitTestAndTrain(0.7); // 70% 的数据用于训练，30% 的数据用于测试
//        DataSetIterator trainIterator = new ListDataSetIterator<>(testAndTrain.getTrain().asList(), batchSize);
//        DataSetIterator testIterator = new ListDataSetIterator<>(testAndTrain.getTest().asList(), batchSize);
//        String modelFilePath = "model/weightFourthMyCnnModel12.zip";
//        String modelFilePath = "model/board1_中+左上+左下+右上+右下的4000局_86.zip";
//        String modelFilePath = "ChessGames\\GoBang\\AI\\weightMyCnnModel_中3600_单纯shuffle_52.zip";
//        String modelFilePath = "model/weightMyCnnModel_全局平均共6000局_43.zip";
//        String modelFilePath = "model/weightMyCnnModel_中3600_调整LR为0.0001_1.zip";
        String modelFilePath = "model/weightMyCnnModel_中3600_去池化+dropout=0.5_141.zip";
//        String modelFilePath = "model/weightMyCnnModel_中9600_调整LR为0.0001_7.zip";
        MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork(modelFilePath);

        INDArray predicted = model.output(features, false);
        System.out.println(boardState);
        for (int i = 0; i < boardState.size(); i++) {
            predicted.putScalar(0, boardState.get(i), 0);
        }


        int xPos = predicted.argMax().toIntVector()[0] / COLS;
        int yPos = predicted.argMax().toIntVector()[0] % ROWS;
        return predicted.argMax().toIntVector()[0];
    }

    /**
     * @param filename
     * @return
     * @description 读取json文件工具类
     */
    public static JSONObject readJsonFile(String filename) {
        String jsonString = "";
        File jsonFile = new File(filename);
        try {
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer stringBuffer = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                stringBuffer.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonString = stringBuffer.toString();
        } catch (FileNotFoundException e) {
            JSONObject notFoundJson = new JSONObject();
//            notFoundJson.put("code",Code.GET_ERR);
            notFoundJson.put("msg", "该文件不存在！");
            return notFoundJson;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONObject.parseObject(jsonString);
    }

    public JSONObject play() {
        //判断传入board是否为空
        boolean emptyFlag = true;
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                if (board[i][j] != 0){
                    emptyFlag = false;
                    break;
                }
            }
        }
        if (emptyFlag) {//先手
            JSONArray jsonElements = new JSONArray();
            jsonElements.add(COLS / 2);
            jsonElements.add(ROWS / 2);
            JSONObject next = new JSONObject();
            next.put("next",jsonElements);
            return next;
//            return new Point(board.COLS / 2, board.ROWS / 2);
        }
        //转换格式
        //处理数据
        ArrayList<Integer> boardState = new ArrayList<>();
        INDArray features = Nd4j.zeros(new int[]{1, 1, 15, 15});
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                if (board[i][j] != 0){
                    if (board[i][j] == 2) {
                        features.putScalar(0, 0, i, j, 1);
                    } else if (board[i][j] == 1) {
                        features.putScalar(0, 0, i, j, -1);
                    }
                    boardState.add((i * ROWS) + j);//棋盘不为空的位置
                }else {
                    features.putScalar(0, 0, i, j, 0);
                }
            }
        }
        //预测
        int index = 0;
        try {
            index = predict(features, boardState);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int xPos = index / COLS;
        int yPos = index % ROWS;
        //封装结果
        JSONArray jsonElements = new JSONArray();
        jsonElements.add(xPos);
        jsonElements.add(yPos);
        JSONObject next = new JSONObject();
        next.put("next",jsonElements);
        return next;
//        return new Point(xPos, yPos);
    }
}