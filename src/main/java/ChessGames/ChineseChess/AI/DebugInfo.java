package ChessGames.ChineseChess.AI;


import java.util.concurrent.atomic.AtomicInteger;



/**
 * <b>Description : </b>
 **/
public class DebugInfo {

    /**
     * CPU线程数
     */
    private final int CPU_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private static final AtomicInteger alphaBeta = new AtomicInteger(0);
    private static final AtomicInteger alphaBetaOrder = new AtomicInteger(0);
    private static final AtomicInteger pollListCount = new AtomicInteger(0);
    private static final AtomicInteger newListCount = new AtomicInteger(0);
    private static final AtomicInteger addListCount = new AtomicInteger(0);

    public static void initAlphaBetaTime() {
        alphaBeta.set(0);
        alphaBetaOrder.set(0);
    }

    public static void logEnd() {

    }

    public static void incrementNewListCount() {
        newListCount.incrementAndGet();
    }

    public static void incrementAddListCount() {
        addListCount.incrementAndGet();
    }

    public static void incrementPollListCount() {
        pollListCount.incrementAndGet();
    }

    public static void incrementAlphaBetaTime() {
        alphaBeta.incrementAndGet();
    }

    public static void incrementAlphaBetaOrderTime() {
        alphaBetaOrder.incrementAndGet();
    }

}
