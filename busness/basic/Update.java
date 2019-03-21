package busness.basic.basic;

import java.sql.Time;
import java.util.Date;

import busness.basic.ibasic.IUpdate;
import persistence.jdbc.dao.daofactory.DAOFactoryCreater;
import persistence.jdbc.daoexception.ConsultorioException;
import persistence.jdbc.model.Agendahora;
import persistence.jdbc.model.Consulta;
import persistence.jdbc.model.Odontologista;
import persistence.jdbc.model.Radiografia;
import persistence.jdbc.model.Usuario;

public class Update implements IUpdate {

    @Override
    public void update(int tipo, Object[] o) throws ConsultorioException {
        switch (tipo) {
            case 1:
                updateStatusAgendaHora(o);
                break;
            case 2:
                updateUsuario(o);
                break;
            case 3:
                updateUsuario(o);
                updateOdontologista(o);
                break;
            case 4:
                updateID_PacienteAgendaHora(o);
                break;
            case 5:
                updateConsulta(o);
                break;
            case 6:
                updateRaioXDescricao(o);
                break;
        }
    }

    private void updateRaioXDescricao(Object o[]) throws ConsultorioException{
        Radiografia r = new Radiografia();
        
        r.setId((int) o[0]);
        r.setDescricao((String) o[1]);
        
        new DAOFactoryCreater().getFactry().createRadiografiaDAO().update(r.getId(), r);
    }
    
    private void updateConsulta(Object[] o) throws ConsultorioException{
        Consulta c = new Consulta();
        
        c.setId((int) o[0]);
        c.setId_paciente((int) o[1]);
        c.setId_agenda((int) o[2]);
        c.setId_agendahora((int) o[3]);
        c.setId_odontologista((int) o[4]);
        c.setDescricao((String) o[5]);

        new DAOFactoryCreater().getFactry().createConsultaDAO().update(c);
        
    }
    
    private void updateUsuario(Object[] o) throws ConsultorioException {
        Usuario u = new Usuario();

        u.setId((int) o[0]);
        u.setNome((String) o[1]);
        u.setCpf((String) o[2]);
        u.setTelefone((String) o[3]);
        u.setEndereco((String) o[4]);
        u.setUsuario((String) o[5]);
        u.setSenha((String) o[6]);
        u.setAutorizacao((int) o[7]);

        new DAOFactoryCreater().getFactry().createUsuarioDAO().update(u.getId(), u);
    }

    private void updateOdontologista(Object[] o) throws ConsultorioException {
        Odontologista od = new Odontologista();

        od.setId((int) o[8]);
        od.setId_usuario((int) o[0]);
        od.setCro((String) o[9]);
        
        new DAOFactoryCreater().getFactry().createOdontologistaDAO().update(od.getId(), od);
    }

    private void updateID_PacienteAgendaHora(Object[] o) throws ConsultorioException {
        Agendahora ah = new Agendahora();
        
        ah.setId_agenda((int) o[0]);
        
        ah.setHora(new Time(((Time) o[1]).getTime()));
        ah.setId_paciente((int) o[2]);

        new DAOFactoryCreater().getFactry().createAgendahoraDAO().updateID_Paciente(ah);
    }
    
    private void updateStatusAgendaHora(Object[] o) throws ConsultorioException {
        Agendahora ah = new Agendahora();
        
        ah.setId_agenda((int) o[0]);
        
        ah.setHora(new Time(((Date) o[1]).getTime()));
        ah.setStatus((String) o[2]);

        new DAOFactoryCreater().getFactry().createAgendahoraDAO().updateStatus(ah);
    }

}
