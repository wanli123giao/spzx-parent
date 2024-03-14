package com.test.spzx.manager.mapper;

import com.test.spzx.model.dto.system.SysRoleDto;
import com.test.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    List<SysRole> findByPage(SysRoleDto sysRoleDto);

    void save(SysRole sysRole);



    void update(SysRole sysRole);

    void delete(Integer role_id);

    List<SysRole> findAll();


}
