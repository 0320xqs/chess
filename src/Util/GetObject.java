package Util;

public class GetObject {


    /** 
     *  @Date 18:40 2023/4/12
     *  @Param 
     *  @Descrition 
     *  @Return 
     **/
    
    public static Class<?> getClass(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        return clazz;
    }


}
