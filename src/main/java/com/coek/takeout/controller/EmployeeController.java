package com.coek.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coek.takeout.common.R;
import com.coek.takeout.domain.entity.Employee;
import com.coek.takeout.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author MaakCheukVing
 * @create 2022-06-26 16:13
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param employee
     * @param request
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody Employee employee, HttpServletRequest request) {

        log.info("用户进行登录操作...");

        // MD5加密密码
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 设置查询条件
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        // 用户不存在
        if (emp == null) {
            return R.error("用户不存在");
        }

        // 密码不相等
        if (!emp.getPassword().equals(password)) {
            return R.error("密码错误");
        }

        // 账号状态
        if (emp.getStatus() == 0) {
            return R.error("账号已被锁");
        }

        // 登录成功，存入Session
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * 员工登出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R logout(HttpServletRequest request) {

        log.info("用户进行退出操作...");

        // 退出登录，清空Session
        request.getSession().getAttribute("employee");
        return R.success("已退出登录");
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> saveEmployee(@RequestBody Employee employee,HttpServletRequest request){
        log.info("新增员工，员工信息 {}",employee.toString());
        Long createdId=(Long)request.getSession().getAttribute("employee");

        // 初始化统一密码为123456
        String password=DigestUtils.md5DigestAsHex("123456".getBytes());
        employee.setPassword(password);
//        employee.setCreateUser(createdId);
//        employee.setUpdateUser(createdId);
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());

        // 添加
        employeeService.save(employee);
        return R.success("添加成功");
    }


    /**
     * 分页查询员工列表
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> employeeInfo(Integer page,Integer pageSize,String name){
        log.info("page={},pageSize={},name={}",page,pageSize,name);

        // 创建分页对象
        Page<Employee> pageInfo=new Page<>(page,pageSize);

        // 创建查询对象
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(name),Employee::getName,name);
        // 排序
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 更新员工信息
     * @param employee
     * @param request
     * @return
     */
    @PutMapping
    public R updateInfo(@RequestBody Employee employee,HttpServletRequest  request){
        log.info("更新修改信息: {}",employee.toString());

//        Long empId = getHostId(request);
//        employee.setUpdateUser(empId);
//        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return R.success("更新信息成功");
    }

    /**
     * 根据员工id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R queryEmpById(@PathVariable("id") Long id){

        log.info("需要查询的员工Id:{}",id);
        Employee employee = employeeService.getById(id);
        return R.success(employee);
    }
    /**
     * 获取当前登录用户的id
     * @param request
     * @return
     */
    private Long getHostId(HttpServletRequest request){
        return (Long) request.getSession().getAttribute("employee");
    }
}