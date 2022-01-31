package org.com.iurdapp.utils;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.StringJoiner;

public interface BaseSpecs<T> {

    @NonNull
    default Specification<T> buildSpecAnd(Specification<T> pSpec1) {
        return (root, query, criteriaBuilder) -> {
            Predicate lPredicate1 = pSpec1.toPredicate(root, query, criteriaBuilder);
            return criteriaBuilder.and(lPredicate1);
        };
    }

    @NonNull
    default Specification<T> buildSpecAnd(Specification<T> pSpec1, Specification<T> pSpec2) {
        return (root, query, criteriaBuilder) -> {
            Predicate lPredicate1 = pSpec1.toPredicate(root, query, criteriaBuilder);
            Predicate lPredicate2 = pSpec2.toPredicate(root, query, criteriaBuilder);
            return criteriaBuilder.and(lPredicate1, lPredicate2);
        };
    }

    @NonNull
    default Specification<T> buildSpecOr(Specification<T> pSpec1, Specification<T> pSpec2) {
        return (root, query, criteriaBuilder) -> {
            Predicate lPredicate1 = pSpec1.toPredicate(root, query, criteriaBuilder);
            Predicate lPredicate2 = pSpec2.toPredicate(root, query, criteriaBuilder);
            return criteriaBuilder.or(lPredicate1, lPredicate2);
        };
    }

    @NonNull
    default String concatLike(String field) {
        return new StringJoiner("", "%", "%")
            .add(field.toUpperCase())
            .toString();
    }

    @NonNull
    default String stripAccents(String field) {
        return StringUtils.stripAccents(field);
    }

    @NonNull
    default Specification<T> porLike(SingularAttribute<T, String> attribute, String data) {
        return (root, query, cb) -> {
            if (StringUtils.isNotEmpty(data)) {
                return cb.like(cb.upper(root.get(attribute)), concatLike(data));
            }
            return cb.and();
        };
    }

    @NonNull
    default Specification<T> porLikeNonAccent(SingularAttribute<T, String> attribute, String data) {
        if (StringUtils.isNotBlank(data) && data.contains("%")) {
            data = data.replace("%", "\\%");
        }
        String finalData = data;
        return (root, query, cb) -> {
            if (StringUtils.isNotEmpty(finalData)) {
                return cb.like(cb.upper(cb.function("unaccent", String.class, root.get(attribute))), stripAccents(concatLike(finalData)));
            }
            return cb.and();
        };
    }

    @NonNull
    default <D> Specification<T> porLike(SingularAttribute<T, D> attribute, D data) {
        return (root, query, cb) -> {
            if (data != null) {
                return cb.like(root.get(attribute).as(String.class), concatLike(data.toString()));
            }
            return cb.and();
        };
    }

    @NonNull
    default Specification<T> porEqualsNonAccent(SingularAttribute<T, String> attribute, String data) {
        return (root, query, cb) -> {
            if (StringUtils.isNotEmpty(data)) {
                return cb.equal(cb.upper(cb.function("unaccent", String.class, root.get(attribute))), stripAccents(data).toUpperCase());
            }
            return cb.and();
        };
    }

    @NonNull
    default Specification<T> porEquals(SingularAttribute<T, String> attribute, String data) {
        return (root, query, cb) -> {
            if (StringUtils.isNotEmpty(data)) {
                return cb.equal(cb.upper(root.get(attribute)), data.toUpperCase());
            }
            return cb.and();
        };
    }

    @NonNull
    default <D> Specification<T> porEquals(SingularAttribute<T, D> attribute, D data) {
        return (root, query, cb) -> {
            if (data != null) {
                return cb.equal(root.get(attribute), data);
            }
            return cb.and();
        };
    }

    @NonNull
    default Specification<T> porEqualsNull(SingularAttribute<T, LocalDate> attribute) {
        return (root, query, cb) -> cb.isNull(root.get(attribute));
    }

    @NonNull
    default <D> Specification<T> porNotEquals(SingularAttribute<T, D> attribute, D data) {
        return (root, query, cb) -> {
            if (data != null) {
                return cb.notEqual(root.get(attribute), data);
            }
            return cb.and();
        };
    }

    @NonNull
    default <D> Specification<T> porNotEqualsIn(SingularAttribute<T, D> attribute, List<D> list) {
        return (root, query, cb) -> {
            if (!CollectionUtils.isEmpty(list)) {
                return cb.and(root.get(attribute).in(list).not());
            }
            return cb.and();
        };
    }

    @NonNull
    default <D> Specification<T> porEqualsIn(SingularAttribute<T, D> attribute, List<D> list) {
        return (root, query, cb) -> {
            if (CollectionUtils.isEmpty(list)) {
                return cb.and(root.get(attribute).in(list));
            }
            return cb.and();
        };
    }

    @NonNull
    default Specification<T> porEqualsBoolean(SingularAttribute<T, Boolean> attribute, Boolean bool) {
        return (root, query, cb) -> {
            if (BooleanUtils.isTrue(bool)) {
                return cb.equal(root.get(attribute), bool);
            }
            return cb.and();
        };
    }

    @NonNull
    default <D, S> Specification<T> porEqualsJoin(SingularAttribute<T, D> attribute, S data, SingularAttribute<D, S> attrib) {
        return (root, query, cb) -> {
            if (data != null) {
                return cb.equal(root.get(attribute).get(attrib), data);
            }
            return cb.and();
        };
    }

    @NonNull
    default <D> Specification<T> porLikeNonAccentJoin(SingularAttribute<T, D> attribute, String data, SingularAttribute<D, String> attrib) {
        if (StringUtils.isNotBlank(data) && data.contains("%")) {
            data = data.replace("%", "\\%");
        }
        String finalData = data;
        return (root, query, cb) -> {
            if (StringUtils.isNotEmpty(finalData)) {
                return cb.like(cb.upper(cb.function("unaccent", String.class, root.get(attribute).get(attrib))), stripAccents(concatLike(finalData)));
            }
            return cb.and();
        };
    }

    @NonNull
    default Specification<T> s(SingularAttribute<T, String> attribute, String data) {
        if (StringUtils.isNotBlank(data) && data.contains("%")) {
            data = data.replace("%", "\\%");
        }
        String finalData = data;
        return (root, query, cb) -> {
            if (StringUtils.isNotEmpty(finalData)) {
                return cb.like(cb.upper(cb.function("unaccent", String.class, root.get(attribute))), stripAccents(concatLike(finalData)));
            }
            return cb.and();
        };
    }

    @NonNull
    default Specification<T> porBetweenDate(SingularAttribute<T, Calendar> attribute, Calendar dataBefore, Calendar dataAfter) {
        return (root, query, cb) -> {
            if (dataBefore != null) {
                return cb.between(root.get(attribute), dataBefore, dataAfter);
            }
            return cb.and();
        };
    }

    @NonNull
    default Specification<T> porBetweenDate(SingularAttribute<T, LocalDate> attribute, LocalDate dataBefore, LocalDate dataAfter) {
        return (root, query, cb) -> {
            if (dataAfter != null && dataBefore != null) {
                return cb.between(root.get(attribute), dataBefore, dataAfter);
            }
            return cb.and();
        };
    }

    @NonNull
    default Specification<T> porLessThanOrEqualDate(SingularAttribute<T, LocalDate> attribute, LocalDate data) {
        return (root, query, cb) -> {
            if (data != null) {
                return cb.lessThanOrEqualTo(root.get(attribute), data);
            }
            return cb.and();
        };
    }

    @NonNull
    default Specification<T> porLessThanOrEqualDate(SingularAttribute<T, Calendar> attribute, Calendar data) {
        return (root, query, cb) -> {
            if (data != null) {
                return cb.lessThanOrEqualTo(root.get(attribute), data);
            }
            return cb.and();
        };
    }

    @NonNull
    default Specification<T> porGreaterThanOrEqualDate(SingularAttribute<T, Calendar> attribute, Calendar data) {
        return (root, query, cb) -> {
            if (data != null) {
                return cb.greaterThanOrEqualTo(root.get(attribute), data);
            }
            return cb.and();
        };
    }

    @NonNull
    default Specification<T> porGreaterThanOrEqualDate(SingularAttribute<T, LocalDate> attribute, LocalDate data) {
        return (root, query, cb) -> {
            if (data != null) {
                return cb.greaterThanOrEqualTo(root.get(attribute), data);
            }
            return cb.and();
        };
    }

    @NonNull
    default Specification<T> porGreaterThanOrEqualDate(SingularAttribute<T, LocalDateTime> attribute, LocalDateTime data) {
        return (root, query, cb) -> {
            if (data != null) {
                return cb.greaterThanOrEqualTo(root.get(attribute), data);
            }
            return cb.and();
        };
    }

    @NonNull
    default Specification<T> porGreaterThanOrEqualInteger(SingularAttribute<T, Integer> attribute, Integer data) {
        return (root, query, cb) -> {
            if (data != null) {
                return cb.greaterThanOrEqualTo(root.get(attribute), data);
            }
            return cb.and();
        };
    }

    @NonNull
    default <D, S> Specification<T> porEqualsJoinSet(SetAttribute<T, D> attribute, S data, SingularAttribute<D, S> attrib, JoinType joinType) {
        return (root, query, cb) -> {
            if (data != null) {
                return cb.equal(root.join(attribute, joinType).get(attrib), data);
            }
            return cb.and();
        };
    }

    @NonNull
    default <D> Specification<T> porLikeJoin(SingularAttribute<T, D> attribute, String data, SingularAttribute<D, String> attrib) {
        return (root, query, cb) -> {
            if (data != null) {
                return cb.like(cb.upper(cb.function("unaccent", String.class, root.get(attribute).get(attrib))), stripAccents(concatLike(data)));
            }
            return cb.and();
        };
    }

    @NonNull
    default <D> Specification<T> buildGroupBySpecs(Specification<T> pSpec1, SingularAttribute<T, D> attribute) {
        return (root, query, criteriaBuilder) -> {
            query.groupBy(root.get(attribute));
            return pSpec1.toPredicate(root, query, criteriaBuilder);
        };
    }
}
