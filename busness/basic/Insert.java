package busness.basic.basic;

import busness.basic.basicfactory.BasicFactoryCreater;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import busness.basic.ibasic.IInsert;
import java.sql.Date;
import persistence.jdbc.dao.daofactory.DAOFactoryCreater;
import persistence.jdbc.daoexception.ConsultorioException;
import persistence.jdbc.model.Administrador;
import persistence.jdbc.model.Agenda;
import persistence.jdbc.model.Agendahora;
import persistence.jdbc.model.Consulta;
import persistence.jdbc.model.Odontologista;
import persistence.jdbc.model.Paciente;
import persistence.jdbc.model.Radiografia;
import persistence.jdbc.model.Secretaria;
import persistence.jdbc.model.Usuario;

public class Insert implements IInsert {

    @Override
    public void insert(int tipo, Object[] o) throws ConsultorioException {

        switch (tipo) {
            case 1:
                insertUsuario(o);
                break;
            case 2:
                insertAgendaOdontologista(o);
                break;
            case 3:
                insertConsulta(o);
                break;
            case 4:
                insertRaioX(o);
                break;
        }

    }
    
    private void insertRaioX(Object[] o) throws ConsultorioException{
        Radiografia r = new Radiografia();
        
        r.setId_paciente((int) o[0]);
        r.setImagem((byte[]) o[1]);
	r.setData((Date) o[2]);
	r.setDescricao((String) o[3]);
	
        new DAOFactoryCreater().getFactry().createRadiografiaDAO().insert(r);
    }
    
    private void insertConsulta(Object[] o) throws ConsultorioException {
        Consulta c = new Consulta();
        Object[] ob = new Object[3];

        c.setId_paciente((int) o[0]);
	c.setId_agenda((int) o[1]);
        c.setId_agendahora((int) o[2]);
	c.setId_odontologista((int) o[3]);
        
        new DAOFactoryCreater().getFactry().createConsultaDAO().insert(c);

        ob[0] = o[1];
        ob[1] = o[4];
        ob[2] = o[0];
        
        new BasicFactoryCreater().getFactry().createUpdate().update(4, ob);      
        
    }

    private void insertAgendaOdontologista(Object o[]) throws ConsultorioException {
        insertAgenda(o);

        insertAgendaHora(o);
    }

    private void insertAgendaHora(Object[] o) throws ConsultorioException {
        Agendahora ah = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        java.util.Date data = null;

        for (int i = 8; i < 12; i++) {
            ah = new Agendahora();
            ah.setId_agenda((int) o[2]);
            ah.setId_paciente(-1);
            try {
                data = sdf.parse(Integer.toString(i) + ":00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ah.setHora(new Time(data.getTime()));
            ah.setStatus("livre");
            new DAOFactoryCreater().getFactry().createAgendahoraDAO().insert(ah);
        }

        for (int i = 13; i < 19; i++) {
            ah = new Agendahora();
            ah.setId_agenda((int) o[2]);
            ah.setId_paciente(-1);
            try {
                data = sdf.parse(Integer.toString(i) + ":00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ah.setHora(new Time(data.getTime()));
            ah.setStatus("livre");
            new DAOFactoryCreater().getFactry().createAgendahoraDAO().insert(ah);
        }
    }

    private void insertAgenda(Object[] o) throws ConsultorioException {
        Agenda a = new Agenda();

        a.setId_odontologista((int) o[0]);
        a.setData((Date) o[1]);

        new DAOFactoryCreater().getFactry().createAgendaDAO().insert(a);

        o[2] = new DAOFactoryCreater().getFactry().createAgendaDAO().getRecordCount();
    }

    private void insertUsuario(Object[] o) throws ConsultorioException {
        int id_usuario;
        Usuario u = new Usuario();

        u.setNome((String) o[0]);
        u.setCpf((String) o[1]);
        u.setTelefone((String) o[2]);
        u.setEndereco((String) o[3]);
        u.setUsuario((String) o[4]);
        u.setSenha((String) o[5]);
        u.setAutorizacao((int) o[6]);

        new DAOFactoryCreater().getFactry().createUsuarioDAO().insert(u);

       id_usuario = new DAOFactoryCreater().getFactry().createUsuarioDAO().getRecordCount();

        switch ((int) o[6]) {
            case 1:
                insertAdministrador(id_usuario);
                break;
            case 2:
                insertOdontologista(id_usuario, o);
                break;
            case 3:
                insertSecretaria(id_usuario);
                break;
            case 4:
                insertPaciente(id_usuario);
                break;
        }
    }

    private void insertPaciente(int id_usuario) throws ConsultorioException {
        Paciente pa = new Paciente();
        pa.setId_usuario(id_usuario);
        new DAOFactoryCreater().getFactry().createPacienteDAO().insert(pa);
    }

    private void insertSecretaria(int id_usuario) throws ConsultorioException {
        Secretaria s = new Secretaria();
        s.setId_usuario(id_usuario);
        new DAOFactoryCreater().getFactry().createSecretariaDAO().insert(s);
    }

    private void insertOdontologista(int id_usuario, Object[] o) throws ConsultorioException {
        Odontologista od = new Odontologista();
        od.setId_usuario(id_usuario);
        od.setCro((String) o[7]);
        new DAOFactoryCreater().getFactry().createOdontologistaDAO().insert(od);
    }

    private void insertAdministrador(int id_usuario) throws ConsultorioException {
        Administrador a = new Administrador();
        a.setId_usuario(id_usuario);
        new DAOFactoryCreater().getFactry().createAdministradorDAO().insert(a);
    }

}
