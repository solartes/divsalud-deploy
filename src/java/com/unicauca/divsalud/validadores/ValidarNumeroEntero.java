
package com.unicauca.divsalud.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator(value="ValidarNumeroEntero")
public class ValidarNumeroEntero implements Validator
{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        String texto = String.valueOf(value);
        if(texto.length()==0)return;
        
        System.out.println("texto "+texto);
        try
        {
            long campo= Integer.parseInt(texto);
            
        }catch(Exception e)
        {
           FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Campo solo puede contener números.","Campo solo puede contener números.");
           throw new ValidatorException(msg);
        }
        
        

    }
    
    
    
    
    
}
