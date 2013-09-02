package asis.eshub.agent.domain;


import java.io.Serializable;

public class EmpVo implements Serializable {



    /**
     * 사원 번호
     */
    private Integer empno;

    /**
     * 직원 명
     */
    private String ename;

    /**
     * 담당 업무
     */
    private String job;

    /**
     * 매니저 번호
     */
    private Integer mgr;

    /**
     * 입사 일
     */
    private String hiredate;

    /**
     * 연봉
     */
    private Double sal;

    /**
     * 보너스
     */
    private Double comm;

    /**
     * 부서 번호
     */
    private Integer deptno;


    public EmpVo() {
    }

    public EmpVo(String ename) {
        this.ename = ename;
    }

    public Integer getEmpno() {
        return empno;
    }

    public void setEmpno(Integer empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getMgr() {
        return mgr;
    }

    public void setMgr(Integer mgr) {
        this.mgr = mgr;
    }

    public String getHiredate() {
        return hiredate;
    }

    public void setHiredate(String hiredate) {
        this.hiredate = hiredate;
    }

    public Double getSal() {
        return sal;
    }

    public void setSal(Double sal) {
        this.sal = sal;
    }

    public Double getComm() {
        return comm;
    }

    public void setComm(Double comm) {
        this.comm = comm;
    }

    public Integer getDeptno() {
        return deptno;
    }

    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }

}
