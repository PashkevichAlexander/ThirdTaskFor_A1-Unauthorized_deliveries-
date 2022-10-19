package entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Post implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Getter
    @Setter
    private String matDoc;
    @Getter
    @Setter
    private String item;
    @Getter
    @Setter
    private String docDate;
    @Getter
    @Setter
    private String pstngDate;
    @Getter
    @Setter
    private String materialDescription;
    @Getter
    @Setter
    private String quantity;
    @Getter
    @Setter
    private String bUn;
    @Getter
    @Setter
    private String amountLC;
    @Getter
    @Setter
    private String crcy;
    @Getter
    @Setter
    private String userName;

    @Override
    public String toString()
    {
        return "entity.Employee [matDoc=" + matDoc + ", item=" + item + ",docDate=" + docDate + ", pstngDate=" + pstngDate + ", materialDescription=" + materialDescription
                + ", quantity=" + quantity+ ", bUn=" + bUn+ ", amountLC=" + amountLC+ ", crcy=" + crcy+ ", userName=" + userName+ "]";
    }
}
