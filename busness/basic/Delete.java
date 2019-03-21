package busness.basic.basic;

import busness.basic.ibasic.IDelete;
import persistence.jdbc.dao.daofactory.DAOFactoryCreater;
import persistence.jdbc.daoexception.ConsultorioException;

public class Delete implements IDelete{
    
    @Override
    public void delete(int tipo, Object o) throws ConsultorioException{
        switch(tipo){
            case 1:
                deleteConsulta((int) o);
                break;
        }
    }
    
    private void deleteConsulta(int  id) throws ConsultorioException{
        Object[] o = new Object[3];
        
        new DAOFactoryCreater().getFactry().createConsultaDAO().delete(id);
                        
    }
    
}
