package uk.co.birchlabs;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by birch on 21/06/2016.
 */
public interface IPokemonRepository extends CrudRepository<JMDictPronunciation, Integer> {
}
