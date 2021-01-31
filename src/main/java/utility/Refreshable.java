package utility;

import javafx.concurrent.Task;
import java.util.List;

public interface Refreshable<T> {
    Task<List<T>> refresh();
    boolean isRefreshing();
}
