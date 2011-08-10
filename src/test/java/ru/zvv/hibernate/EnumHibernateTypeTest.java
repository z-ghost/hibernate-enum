package ru.zvv.hibernate;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;

/**
 * @author Vitaly Zubchevskiy
 *         Created at 15:37 08.08.11
 */
public class EnumHibernateTypeTest extends TestCase
{

    private SessionFactory sf;

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        Configuration cfg = new Configuration();
        cfg.configure();
        sf = cfg.buildSessionFactory();

    }

    public void testStateMapping() throws Exception
    {

        Session session = null;
        try
        {
            session = sf.openSession();
            session.beginTransaction();
            Document d = new Document();
            d.setState(State.Draft);
            session.save(d);

            Integer state = (Integer) session.createSQLQuery("select STATE from Document where id = ?")
                    .setParameter(0, d.getId())
                    .uniqueResult();

            Assert.assertNotNull(state);
            Assert.assertEquals(State.Draft.getDbValue(), state.intValue());
        }
        finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }
}
