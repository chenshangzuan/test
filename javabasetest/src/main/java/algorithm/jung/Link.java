package algorithm.jung;

import java.math.BigDecimal;

/**
 * @author: Kled
 * @version: Node.java, v0.1 2020-10-14 14:22 Kled
 */
public class Link {

    private String name;

    private Integer priority;

    private BigDecimal loss;

    public Link(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public BigDecimal getLoss() {
        return loss;
    }

    public void setLoss(BigDecimal loss) {
        this.loss = loss;
    }

    @Override
    public String toString() {
        return "Link{" +
                "name='" + name + '\'' +
                ", priority=" + priority +
                ", loss=" + loss +
                '}';
    }
}
