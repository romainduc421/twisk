package twisk.exceptions;

import twisk.mondeIG.PointDeControleIG;

public class ArcsException extends TwiskException{

    public ArcsException(String errorMsg, PointDeControleIG pt_src, PointDeControleIG pt_dest){
        super(errorMsg);
        System.out.println(pt_src.toString() + "\n" + pt_dest.toString());
    }
}
