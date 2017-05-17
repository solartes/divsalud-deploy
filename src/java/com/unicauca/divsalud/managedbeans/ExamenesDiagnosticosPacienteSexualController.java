/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.divsalud.managedbeans;

// @author acer_acer
import com.unicauca.divsalud.clases.OtroTipoExamen;
import com.unicauca.divsalud.entidades.ExamenesDiagnosticosPacienteSexual;
import com.unicauca.divsalud.entidades.OtrosExamenesDiagnosticosPacienteSexual;
import com.unicauca.divsalud.entidades.OtrosTipoExamDiagnosticoSexual;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

@Named("examenesDiagnosticosPacienteSexualController")
@SessionScoped

public class ExamenesDiagnosticosPacienteSexualController implements Serializable {

    private ExamenesDiagnosticosPacienteSexual examen;
    @EJB
    private com.unicauca.divsalud.sessionbeans.ExamenesDiagnosticosPacienteSexualFacade ejbFacade;
    @EJB
    private com.unicauca.divsalud.sessionbeans.OtrosTipoExamDiagnosticoSexualFacade ejbFacadeOtroTipo;
    @EJB
    private com.unicauca.divsalud.sessionbeans.OtrosExamenesDiagnosticosPacienteSexualFacade ejbFacadeOtroTipoExam;
    private ArrayList<OtroTipoExamen> otrosExamenes;
    String tmp1;
    String tmp2;

    public ExamenesDiagnosticosPacienteSexualController() {
        this.otrosExamenes = new ArrayList<>();
        tmp1 = "";
        tmp2 = "";
    }
    
    public void registro(int idHistoria){
        for (OtroTipoExamen otroExamen : otrosExamenes) {
            OtrosTipoExamDiagnosticoSexual aux = ejbFacadeOtroTipo.findByNombre(otroExamen.getTipo());
            if (aux == null){
                ejbFacadeOtroTipo.create(new OtrosTipoExamDiagnosticoSexual(otroExamen.getTipo()));
                aux = ejbFacadeOtroTipo.findByNombre(otroExamen.getTipo());
            }            
            OtrosExamenesDiagnosticosPacienteSexual otro=new OtrosExamenesDiagnosticosPacienteSexual(idHistoria, aux.getIdOtroExamDiag());
            otro.setEspecificacionExamenFisico(otroExamen.getDescripcion());
            ejbFacadeOtroTipoExam.create(otro);
        }
    }
    
    private boolean verificarDiagnosticoEnLista(String tipo){
        for (OtroTipoExamen otro:otrosExamenes){
            if (otro.getTipo().equals(tipo)) return true;
        }
        return false;
    }

    public void añadirExamen() {
        boolean estaMetodo=verificarDiagnosticoEnLista(tmp1);
        
        if(estaMetodo)
        {
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('DiagnosticoNoAgregado').show()");
            //limpiarCampos();
            return;
        }
        otrosExamenes.add(new OtroTipoExamen(tmp1, tmp2));
        limpiar();
    }

    public ExamenesDiagnosticosPacienteSexual getExamen() {
        return examen;
    }

    public void setExamen(ExamenesDiagnosticosPacienteSexual examen) {
        this.examen = examen;
    }

    public ArrayList<OtroTipoExamen> getOtrosExamenes() {
        return otrosExamenes;
    }

    public void setOtrosExamenes(ArrayList<OtroTipoExamen> otrosExamenes) {
        this.otrosExamenes = otrosExamenes;
    }
    
    public String getTmp1() {
        return tmp1;
    }

    public void setTmp1(String tmp1) {
        this.tmp1 = tmp1;
    }

    public String getTmp2() {
        return tmp2;
    }

    public void setTmp2(String tmp2) {
        this.tmp2 = tmp2;
    }

    private void limpiar() {
        tmp1="";
        tmp2="";
    }
    
    public void removeExamen(OtroTipoExamen otroExamen) {
        System.out.println(" SE INVOCÓ REMOVER DIAGNÓSTICO");

        for (int i = 0; i < this.otrosExamenes.size(); i++) {
           
            
             if (Objects.equals(otrosExamenes.get(i).getTipo(), otroExamen.getTipo()))
             {  
                 otrosExamenes.remove(i);
                 return;
            }
        }

    }

}
