package bean;

import java.sql.Date;

public class Order {
    private int orderId;
    private String orderName;
    private Date ordreDate;

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderName='" + orderName + '\'' +
                ", ordreDate=" + ordreDate +
                '}';
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Date getOrdreDate() {
        return ordreDate;
    }

    public void setOrdreDate(Date ordreDate) {
        this.ordreDate = ordreDate;
    }

    public Order() {
    }

    public Order(int orderId, String orderName, Date ordreDate) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.ordreDate = ordreDate;
    }
}
