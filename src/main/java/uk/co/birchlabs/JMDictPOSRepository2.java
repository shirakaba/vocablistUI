package uk.co.birchlabs;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by jamiebirch on 24/06/2016.
 */
@Repository
public class JMDictPOSRepository2 {
    @PersistenceContext
//    @Autowired
    EntityManager em;

    // Nothing implemented yet.
}
