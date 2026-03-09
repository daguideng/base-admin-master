package cn.huanzi.qch.baseadmin.entity;

import java.util.Date;

public class Adas_move_cases {
    private Integer id;

    private String case_id;

    private String scenario_editor_name;

    private String scenario_editor_id;

    private String choice_laws;

    private String select_case;

    private String remark;

    private String remark1;

    private String remark2;

    private Date create_time;

    private Date update_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String case_id) {
        this.case_id = case_id == null ? null : case_id.trim();
    }

    public String getScenario_editor_name() {
        return scenario_editor_name;
    }

    public void setScenario_editor_name(String scenario_editor_name) {
        this.scenario_editor_name = scenario_editor_name == null ? null : scenario_editor_name.trim();
    }

    public String getScenario_editor_id() {
        return scenario_editor_id;
    }

    public void setScenario_editor_id(String scenario_editor_id) {
        this.scenario_editor_id = scenario_editor_id == null ? null : scenario_editor_id.trim();
    }

    public String getChoice_laws() {
        return choice_laws;
    }

    public void setChoice_laws(String choice_laws) {
        this.choice_laws = choice_laws == null ? null : choice_laws.trim();
    }

    public String getSelect_case() {
        return select_case;
    }

    public void setSelect_case(String select_case) {
        this.select_case = select_case == null ? null : select_case.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Adas_move_cases(){}

    public Adas_move_cases(String case_id, String scenario_editor_name, String scenario_editor_id, String choice_laws, String select_case, String remark, String remark1, String remark2, Date create_time, Date update_time) {
        this.case_id = case_id;
        this.scenario_editor_name = scenario_editor_name;
        this.scenario_editor_id = scenario_editor_id;
        this.choice_laws = choice_laws;
        this.select_case = select_case;
        this.remark = remark;
        this.remark1 = remark1;
        this.remark2 = remark2;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public Adas_move_cases(String case_id, String scenario_editor_name, String scenario_editor_id, String choice_laws, String select_case, Date create_time, Date update_time) {
        this.case_id = case_id;
        this.scenario_editor_name = scenario_editor_name;
        this.scenario_editor_id = scenario_editor_id;
        this.choice_laws = choice_laws;
        this.select_case = select_case;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public Adas_move_cases(String case_id, String scenario_editor_name, String scenario_editor_id, String choice_laws, String select_case) {
        this.case_id = case_id;
        this.scenario_editor_name = scenario_editor_name;
        this.scenario_editor_id = scenario_editor_id;
        this.choice_laws = choice_laws;
        this.select_case = select_case;
    }

    public Adas_move_cases(String scenario_editor_id, String choice_laws, String select_case, Date create_time, Date update_time) {
        this.scenario_editor_id = scenario_editor_id;
        this.choice_laws = choice_laws;
        this.select_case = select_case;
        this.create_time = create_time;
        this.update_time = update_time;
    }
}