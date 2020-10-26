package edu.javacourse.city.dao;

import edu.javacourse.city.domain.PersonRequest;
import edu.javacourse.city.domain.PersonResponse;
import edu.javacourse.city.exception.PersonCheckException;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class PersonCheckDaoTest {

    @Test
    public void checkPerson() throws PersonCheckException {
        PersonRequest pr = new PersonRequest();
        pr.setSurName("Денисов");
        pr.setGivenName("Никита");
        pr.setPatronymic("Иванович");
        pr.setDateOfBirth(LocalDate.of(2010, 02, 01));
        pr.setStreetCode(1);
        pr.setBuilding("124");
        pr.setExtension("3");
        pr.setApartment("17");

        PersonCheckDao dao = new PersonCheckDao();
//        dao.setConnectionBuilder(new DirectConnectionBuilder());
        PersonResponse ps = dao.checkPerson(pr);
        assertTrue(ps.isRegistered());
        assertFalse(ps.isTemporal());
    }

    @Test
    public void checkPerson2() throws PersonCheckException {
        PersonRequest pr = new PersonRequest();
        pr.setSurName("Денисов");
        pr.setGivenName("Иван");
        pr.setPatronymic("Александрович");
        pr.setDateOfBirth(LocalDate.of(1979, 10, 17));
        pr.setStreetCode(1);
        pr.setBuilding("124");
        pr.setExtension("3");
        pr.setApartment("17");

        PersonCheckDao dao = new PersonCheckDao();
//        dao.setConnectionBuilder(new DirectConnectionBuilder());
        PersonResponse ps = dao.checkPerson(pr);
        assertTrue(ps.isRegistered());
        assertFalse(ps.isTemporal());
    }
}