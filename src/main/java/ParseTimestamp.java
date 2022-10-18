import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

import java.sql.Timestamp;

public class ParseTimestamp extends CellProcessorAdaptor {


    public ParseTimestamp() {
        super();
    }

    public ParseTimestamp(CellProcessor next) {
        super(next);
    }

    @Override
    public Object execute(Object value, CsvContext context) {
        return Timestamp.valueOf((String) value);
    }
}
