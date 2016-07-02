package uk.co.birchlabs;

import org.springframework.data.repository.CrudRepository;

/**
 * eg. 為る
 */
public interface JMDictWordRepo extends CrudRepository<JMDictWord, IdDataKey> {
}
