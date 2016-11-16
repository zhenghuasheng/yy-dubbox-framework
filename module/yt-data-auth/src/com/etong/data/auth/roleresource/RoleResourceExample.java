package com.etong.data.auth.roleresource;

import com.etong.pt.utility.PageLimit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoleResourceExample extends PageLimit implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_pm_roleresource
     *
     * @mbggenerated Tue Dec 15 11:46:27 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_pm_roleresource
     *
     * @mbggenerated Tue Dec 15 11:46:27 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_pm_roleresource
     *
     * @mbggenerated Tue Dec 15 11:46:27 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_roleresource
     *
     * @mbggenerated Tue Dec 15 11:46:27 CST 2015
     */
    public RoleResourceExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_roleresource
     *
     * @mbggenerated Tue Dec 15 11:46:27 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_roleresource
     *
     * @mbggenerated Tue Dec 15 11:46:27 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_roleresource
     *
     * @mbggenerated Tue Dec 15 11:46:27 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_roleresource
     *
     * @mbggenerated Tue Dec 15 11:46:27 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_roleresource
     *
     * @mbggenerated Tue Dec 15 11:46:27 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_roleresource
     *
     * @mbggenerated Tue Dec 15 11:46:27 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_roleresource
     *
     * @mbggenerated Tue Dec 15 11:46:27 CST 2015
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_roleresource
     *
     * @mbggenerated Tue Dec 15 11:46:27 CST 2015
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_roleresource
     *
     * @mbggenerated Tue Dec 15 11:46:27 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_roleresource
     *
     * @mbggenerated Tue Dec 15 11:46:27 CST 2015
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_pm_roleresource
     *
     * @mbggenerated Tue Dec 15 11:46:27 CST 2015
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("f_id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("f_id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("f_id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("f_id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("f_id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("f_id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("f_id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("f_id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("f_id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("f_id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("f_id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("f_id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andRlidIsNull() {
            addCriterion("f_rlid is null");
            return (Criteria) this;
        }

        public Criteria andRlidIsNotNull() {
            addCriterion("f_rlid is not null");
            return (Criteria) this;
        }

        public Criteria andRlidEqualTo(Long value) {
            addCriterion("f_rlid =", value, "rlid");
            return (Criteria) this;
        }

        public Criteria andRlidNotEqualTo(Long value) {
            addCriterion("f_rlid <>", value, "rlid");
            return (Criteria) this;
        }

        public Criteria andRlidGreaterThan(Long value) {
            addCriterion("f_rlid >", value, "rlid");
            return (Criteria) this;
        }

        public Criteria andRlidGreaterThanOrEqualTo(Long value) {
            addCriterion("f_rlid >=", value, "rlid");
            return (Criteria) this;
        }

        public Criteria andRlidLessThan(Long value) {
            addCriterion("f_rlid <", value, "rlid");
            return (Criteria) this;
        }

        public Criteria andRlidLessThanOrEqualTo(Long value) {
            addCriterion("f_rlid <=", value, "rlid");
            return (Criteria) this;
        }

        public Criteria andRlidIn(List<Long> values) {
            addCriterion("f_rlid in", values, "rlid");
            return (Criteria) this;
        }

        public Criteria andRlidNotIn(List<Long> values) {
            addCriterion("f_rlid not in", values, "rlid");
            return (Criteria) this;
        }

        public Criteria andRlidBetween(Long value1, Long value2) {
            addCriterion("f_rlid between", value1, value2, "rlid");
            return (Criteria) this;
        }

        public Criteria andRlidNotBetween(Long value1, Long value2) {
            addCriterion("f_rlid not between", value1, value2, "rlid");
            return (Criteria) this;
        }

        public Criteria andRsidIsNull() {
            addCriterion("f_rsid is null");
            return (Criteria) this;
        }

        public Criteria andRsidIsNotNull() {
            addCriterion("f_rsid is not null");
            return (Criteria) this;
        }

        public Criteria andRsidEqualTo(Long value) {
            addCriterion("f_rsid =", value, "rsid");
            return (Criteria) this;
        }

        public Criteria andRsidNotEqualTo(Long value) {
            addCriterion("f_rsid <>", value, "rsid");
            return (Criteria) this;
        }

        public Criteria andRsidGreaterThan(Long value) {
            addCriterion("f_rsid >", value, "rsid");
            return (Criteria) this;
        }

        public Criteria andRsidGreaterThanOrEqualTo(Long value) {
            addCriterion("f_rsid >=", value, "rsid");
            return (Criteria) this;
        }

        public Criteria andRsidLessThan(Long value) {
            addCriterion("f_rsid <", value, "rsid");
            return (Criteria) this;
        }

        public Criteria andRsidLessThanOrEqualTo(Long value) {
            addCriterion("f_rsid <=", value, "rsid");
            return (Criteria) this;
        }

        public Criteria andRsidIn(List<Long> values) {
            addCriterion("f_rsid in", values, "rsid");
            return (Criteria) this;
        }

        public Criteria andRsidNotIn(List<Long> values) {
            addCriterion("f_rsid not in", values, "rsid");
            return (Criteria) this;
        }

        public Criteria andRsidBetween(Long value1, Long value2) {
            addCriterion("f_rsid between", value1, value2, "rsid");
            return (Criteria) this;
        }

        public Criteria andRsidNotBetween(Long value1, Long value2) {
            addCriterion("f_rsid not between", value1, value2, "rsid");
            return (Criteria) this;
        }

        public Criteria andAvailableIsNull() {
            addCriterion("f_available is null");
            return (Criteria) this;
        }

        public Criteria andAvailableIsNotNull() {
            addCriterion("f_available is not null");
            return (Criteria) this;
        }

        public Criteria andAvailableEqualTo(Boolean value) {
            addCriterion("f_available =", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableNotEqualTo(Boolean value) {
            addCriterion("f_available <>", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableGreaterThan(Boolean value) {
            addCriterion("f_available >", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableGreaterThanOrEqualTo(Boolean value) {
            addCriterion("f_available >=", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableLessThan(Boolean value) {
            addCriterion("f_available <", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableLessThanOrEqualTo(Boolean value) {
            addCriterion("f_available <=", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableIn(List<Boolean> values) {
            addCriterion("f_available in", values, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableNotIn(List<Boolean> values) {
            addCriterion("f_available not in", values, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableBetween(Boolean value1, Boolean value2) {
            addCriterion("f_available between", value1, value2, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableNotBetween(Boolean value1, Boolean value2) {
            addCriterion("f_available not between", value1, value2, "available");
            return (Criteria) this;
        }

        public Criteria andStidIsNull() {
            addCriterion("f_stid is null");
            return (Criteria) this;
        }

        public Criteria andStidIsNotNull() {
            addCriterion("f_stid is not null");
            return (Criteria) this;
        }

        public Criteria andStidEqualTo(String value) {
            addCriterion("f_stid =", value, "stid");
            return (Criteria) this;
        }

        public Criteria andStidNotEqualTo(String value) {
            addCriterion("f_stid <>", value, "stid");
            return (Criteria) this;
        }

        public Criteria andStidGreaterThan(String value) {
            addCriterion("f_stid >", value, "stid");
            return (Criteria) this;
        }

        public Criteria andStidGreaterThanOrEqualTo(String value) {
            addCriterion("f_stid >=", value, "stid");
            return (Criteria) this;
        }

        public Criteria andStidLessThan(String value) {
            addCriterion("f_stid <", value, "stid");
            return (Criteria) this;
        }

        public Criteria andStidLessThanOrEqualTo(String value) {
            addCriterion("f_stid <=", value, "stid");
            return (Criteria) this;
        }

        public Criteria andStidLike(String value) {
            addCriterion("f_stid like", value, "stid");
            return (Criteria) this;
        }

        public Criteria andStidNotLike(String value) {
            addCriterion("f_stid not like", value, "stid");
            return (Criteria) this;
        }

        public Criteria andStidIn(List<String> values) {
            addCriterion("f_stid in", values, "stid");
            return (Criteria) this;
        }

        public Criteria andStidNotIn(List<String> values) {
            addCriterion("f_stid not in", values, "stid");
            return (Criteria) this;
        }

        public Criteria andStidBetween(String value1, String value2) {
            addCriterion("f_stid between", value1, value2, "stid");
            return (Criteria) this;
        }

        public Criteria andStidNotBetween(String value1, String value2) {
            addCriterion("f_stid not between", value1, value2, "stid");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_pm_roleresource
     *
     * @mbggenerated do_not_delete_during_merge Tue Dec 15 11:46:27 CST 2015
     */
    public static class Criteria extends GeneratedCriteria implements Serializable {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_pm_roleresource
     *
     * @mbggenerated Tue Dec 15 11:46:27 CST 2015
     */
    public static class Criterion implements Serializable {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}