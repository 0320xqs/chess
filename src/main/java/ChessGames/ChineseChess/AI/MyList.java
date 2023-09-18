package ChessGames.ChineseChess.AI;

import java.util.List;
import java.util.function.Predicate;

/**
 * <b>Description : </b>
 **/
public interface MyList<E> extends List<E>, AutoCloseable {

    Object[] eleTemplateDate();

    MyList<E> filter(Predicate<? super Object> predicate);

}
