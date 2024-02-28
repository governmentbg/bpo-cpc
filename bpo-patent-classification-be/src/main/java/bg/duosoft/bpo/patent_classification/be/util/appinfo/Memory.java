package bg.duosoft.bpo.patent_classification.be.util.appinfo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class Memory {

    private double total;
    private double max;
    private double free;

    public static Memory getInfo() {
        return Memory.builder()
                .total(toMegabytes(Runtime.getRuntime().totalMemory()))
                .max(toMegabytes(Runtime.getRuntime().maxMemory()))
                .free(toMegabytes(Runtime.getRuntime().freeMemory()))
                .build();
    }

    public static double toMegabytes(double value) {
        return value / (1024.0 * 1024.0);
    }
}