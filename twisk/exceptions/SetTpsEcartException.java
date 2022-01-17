package twisk.exceptions;

public class SetTpsEcartException extends TwiskException{

    public SetTpsEcartException(String errorMsg, String inpt){
        super(errorMsg) ;
        System.err.println("Vous avez saisi : " +inpt);
    }

}
