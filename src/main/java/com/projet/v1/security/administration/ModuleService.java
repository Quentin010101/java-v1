package com.projet.v1.security.administration;

import com.projet.v1.security.administration.userConfiguration.ModuleEnum;
import com.projet.v1.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService {

    public boolean hasModule(ModuleEnum module){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return isModulePresent(user.getConfig().getModules(), module);
    }

    private boolean isModulePresent(List<ModuleEnum> list, ModuleEnum module){
        return list.contains(module);
    }
}
