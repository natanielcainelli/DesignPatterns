package busness.basic.basic;

import java.sql.Date;
import java.util.List;

import busness.basic.basicfactory.BasicFactoryCreater;
import busness.basic.ibasic.IFind;
import persistence.jdbc.dao.daofactory.DAOFactoryCreater;
import persistence.jdbc.daoexception.ConsultorioException;
import persistence.jdbc.model.Odontologista;
import persistence.jdbc.model.Paciente;
import persistence.jdbc.model.Radiografia;
import persistence.jdbc.model.Usuario;
import persistence.jdbc.model.other.AgendaOdontologista;
import persistence.jdbc.model.other.AgendaPaciente;
import persistence.jdbc.model.other.ImagemDatasPaciente;

public class Find implements IFind {

    @Override
    public Usuario getUsuario(int tipo, Object o) throws ConsultorioException {
        Usuario u = null;

        switch (tipo) {
            case 1:
                u = new DAOFactoryCreater().getFactry().createUsuarioDAO().findByPrimaryKey((int) o);
                break;
            case 2:
                u = new DAOFactoryCreater().getFactry().createUsuarioDAO().findByNome((String) o);
                break;
            case 3:
                u = new DAOFactoryCreater().getFactry().createUsuarioDAO().findByCpf((String) o);
                break;
            case 4:
                u = new DAOFactoryCreater().getFactry().createUsuarioDAO().findByUsuario((String) o);
                break;
        }

        return u;
    }

    @Override
    public List<Usuario> getUsuarios(int autorizacao) throws ConsultorioException {
        return new DAOFactoryCreater().getFactry().createUsuarioDAO().findByAutorizacao(autorizacao);
    }

    @Override
    public Odontologista getOdontologista(int tipo, Object o) throws ConsultorioException {
        switch (tipo) {
            case 1:
                return new DAOFactoryCreater().getFactry().createOdontologistaDAO().findByCro((String) o);
            case 2:
                return new DAOFactoryCreater().getFactry().createOdontologistaDAO().findById_usuario((int) o);
        }
        return null;
    }

    @Override
    public Paciente getPaciente(int tipo, Object o) throws ConsultorioException {
        switch (tipo) {
            case 1:
                return new DAOFactoryCreater().getFactry().createPacienteDAO().findById_usuario((int) o);
        }
        return null;
    }

    @Override
    public List<AgendaOdontologista> getAgendaOdontologista(int id_odontologista, Date data)
            throws ConsultorioException {
        List<AgendaOdontologista> ao = null;
        Object[] o = new Object[3];

        o[0] = id_odontologista;
        o[1] = data;

        ao = new DAOFactoryCreater().getFactry().createAgendaOdontologistaDAO()
                .findExecutingUserSelect_AgendaOdontologista(id_odontologista, data);

        if (ao.isEmpty()) {

            new BasicFactoryCreater().getFactry().createInsert().insert(2, o);

            ao = new DAOFactoryCreater().getFactry().createAgendaOdontologistaDAO()
                    .findExecutingUserSelect_AgendaOdontologista(id_odontologista, data);
        }

        return ao;
    }

    @Override
    public List<AgendaPaciente> getAgendaPaciente(int id_paciente) throws ConsultorioException {

        return new DAOFactoryCreater().getFactry().createAgendaPacienteDAO().findExecutingUserSelect_AgendaPaciente(id_paciente);

    }

    @Override
    public List<ImagemDatasPaciente> getImagemDatasPaciente(int id_paciente) throws ConsultorioException {

        return new DAOFactoryCreater().getFactry().createRadiografiaDAO().findByDatas_Paciente(id_paciente);

    }

    @Override
    public Radiografia getRadiografia(int tipo, Object o) throws ConsultorioException {
        
        switch (tipo) {
            case 1:
                return new DAOFactoryCreater().getFactry().createRadiografiaDAO().findByPrimaryKey((int) o);

        }

        return null;
    }

}
