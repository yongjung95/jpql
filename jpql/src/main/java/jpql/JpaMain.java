package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager(); // 고객의 요청이나 데이터 베이스 작업이 필요한 경우 꼭 EntityManager 를 통해서 해야 한다.
        // 동작하는 코드 작성

        EntityTransaction tx = em.getTransaction(); // JPA 의 모든 데이터 변경은 트랜잭션 안에서 해야한다.
        tx.begin();

        try {

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.changeTeam(team);

            em.persist(member);

            int result = em.createQuery("select size(t.members) from Team t", Integer.class).getSingleResult();

            System.out.println(result);
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
