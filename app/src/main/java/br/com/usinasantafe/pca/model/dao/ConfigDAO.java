package br.com.usinasantafe.pca.model.dao;

import java.util.List;

import br.com.usinasantafe.pca.model.bean.variaveis.ConfigBean;

public class ConfigDAO {

    public ConfigDAO() {
    }

    public boolean hasElements(){
        ConfigBean configBean = new ConfigBean();
        return configBean.hasElements();
    }

    public Long getStatusConfig(){
        ConfigBean configBean = getConfig();
        return configBean.getStatusAplic();
    }

    public void salvarConfig(Long nroAparelho, String senha){
        ConfigBean configBean = new ConfigBean();
        configBean.deleteAll();
        configBean.setNroAparelhoConfig(nroAparelho);
        configBean.setSenhaConfig(senha);
        configBean.setDthrServConfig("");
        configBean.setStatusAplic(0L);
        configBean.insert();
        configBean.commit();
    }

    public void setStatusAplicFechado(){
        ConfigBean configBean = getConfig();
        configBean.setStatusAplic(0L);
        configBean.update();
    }

    public void setMatricUsuario(Long matricUsuario){
        ConfigBean configBean = getConfig();
        configBean.setMatricMotorista(matricUsuario);
        configBean.update();
    }

    public void setIdEquip(Long idEquip){
        ConfigBean configBean = getConfig();
        configBean.setIdEquip(idEquip);
        configBean.setStatusAplic(1L);
        configBean.update();
    }

    public ConfigBean getConfig(){
        List<ConfigBean> configList = configList();
        ConfigBean configBean = configList.get(0);
        configList.clear();
        return configBean;
    }

    public boolean getConfig(String senha){
        List<ConfigBean> configList = configList(senha);
        boolean ret = configList.size() > 0;
        configList.clear();
        return ret;
    }

    private List configList(){
        ConfigBean configBean = new ConfigBean();
        return configBean.all();
    }

    private List configList(String senha){
        ConfigBean configBean = new ConfigBean();
        return configBean.get("senhaConfig", senha);
    }


}
