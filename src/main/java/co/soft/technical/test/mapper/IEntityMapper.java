package co.soft.technical.test.mapper;

import java.util.List;

public interface IEntityMapper<D, E> {

    E toDomain(D dto);

    D toDto(E entity);

}