package edu.stthomas.model;

import edu.stthomas.enums.Shift;
import edu.stthomas.helper.Helper;
import edu.stthomas.repo.SalesRepo;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractRecord {
    protected List<SalesLineItem> salesLineItems;
    protected Cashier cashier;
    protected Shift shift;
    protected Register register;
    protected double totalAmtBeforeTax;
    protected double totalTaxAmt;
    protected Date transactionTime;
    protected SalesRepo salesRepo;
    private int id;

    public AbstractRecord(int cashierId, Shift shift, int registerId) {
        this.salesLineItems = new ArrayList<>();
        this.cashier = new Cashier(cashierId);
        this.shift = shift;
        this.register = new Register(registerId);
        for (SalesLineItem salesLineItem : salesLineItems) {
            totalAmtBeforeTax += salesLineItem.getLineItemSale();
            totalTaxAmt += salesLineItem.getLineItemTax();
        }
        for (SalesLineItem salesLineItem : salesLineItems) {
            totalAmtBeforeTax += salesLineItem.getLineItemSale();
            totalTaxAmt += salesLineItem.getLineItemTax();
        }
        transactionTime = Date.from(ZonedDateTime.now().toInstant());
    }

//    public abstract int save(AbstractRecord salesRecord);//{
//        setId(salesRepo.save(salesRecord));
//        return id;
//    }

    public void setId(int id) {
        this.id = id;
    }

    public AbstractRecord getRecord(int id) {
        return SalesRepo.getSalesRecord(id);
    }

    public int getId() {
        return id;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    /**
     * get the total of sales from each line item (excludes tax amt)
     * @return
     */
    public double getTotalAmtBeforeTax() {
        return Helper.roundUp(totalAmtBeforeTax);
    }

    public double getTotalTaxAmt() {
        return Helper.roundUp(totalTaxAmt);
    }

    public double getTotalAmt() {
        return Helper.roundUp(getTotalAmtBeforeTax() + getTotalTaxAmt());
    }

    public List<SalesLineItem> getSalesLineItems() {
        return salesLineItems;
    }

    public Register getRegister() {
        return register;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public Shift getShift() {
        return shift;
    }
}
