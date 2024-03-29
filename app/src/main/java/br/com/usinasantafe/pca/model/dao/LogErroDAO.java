package br.com.usinasantafe.pca.model.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import br.com.usinasantafe.pca.model.bean.variaveis.LogErroBean;
import br.com.usinasantafe.pca.util.Tempo;

public class LogErroDAO {

    private static LogErroDAO instance = null;

    public static LogErroDAO getInstance() {
        if (instance == null)
            instance = new LogErroDAO();
        return instance;
    }

    public void insertLogErro(Throwable ex){
        LogErroBean logErroBean = new LogErroBean();
        logErroBean.setException(throwableToString(ex));
        logErroBean.setDthr(Tempo.getInstance().dthrAtualString());
        logErroBean.setDthrLong(Tempo.getInstance().dthrStringToLong(Tempo.getInstance().dthrAtualString()));
        logErroBean.setStatus(1L);
        logErroBean.insert();
    }

    public void insertLogErro(String erro){
        LogErroBean logErroBean = new LogErroBean();
        logErroBean.setException("RETORNO SERVIDOR COM FALHA = " + erro);
        logErroBean.setDthr(Tempo.getInstance().dthrAtualString());
        logErroBean.setDthrLong(Tempo.getInstance().dthrStringToLong(Tempo.getInstance().dthrAtualString()));
        logErroBean.setStatus(1L);
        logErroBean.insert();
    }

    private String throwableToString(Throwable t) {

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter, false);

        t.printStackTrace(printWriter);

        printWriter.flush();
        stringWriter.flush();

        return stringWriter.toString();

    }

    public void deleteLogErro(){
        LogErroBean logErroBean = new LogErroBean();
        List<LogErroBean> logErroList = logErroBean.all();
        for(LogErroBean logErroBeanBD : logErroList){
            if(logErroBeanBD.getDthrLong() < Tempo.getInstance().dthrLongDiaMenos(15)){
                logErroBeanBD.delete();
            }
        }
    }
}
