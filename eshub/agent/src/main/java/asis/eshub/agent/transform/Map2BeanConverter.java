package asis.eshub.agent.transform;

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;
import org.apache.commons.beanutils.BeanUtils;

import asis.eshub.agent.domain.EmpVo;

/**
 * User: jeongtak
 * Date: 13. 7. 1
 * Time: 오전 10:30
 */
public final class Map2BeanConverter implements DataFormat {

    public void marshal(Exchange exchange, Object obj, OutputStream outputStream) throws Exception {

        List<Map> source = exchange.getIn().getBody(List.class);
        List<EmpVo> target = new ArrayList<EmpVo>();

        for(Map map : source){
            EmpVo emp = new EmpVo();
//            BeanUtils.copyProperties(map, emp);
            BeanUtils.populate(target,map);
            target.add(emp);
        }

        ObjectOutputStream o = new ObjectOutputStream(outputStream);
        o.writeObject(target);

//        DataOutputStream out = new DataOutputStream(outputStream);
//
//        for(Map map : source){
//            EmpVo emp = new EmpVo();
//            BeanUtils.copyProperties(map, emp);
//            target.add(emp);
//
////            out.wr
//        }
//        outputStream.write(exchange.getContext().getTypeConverter().mandatoryConvertTo(byte[].class, target));
    }

    public Object unmarshal(Exchange exchange, InputStream inputStream) throws Exception {
//        byte[] bytes = exchange.getContext().getTypeConverter().mandatoryConvertTo(byte[].class, inputStream);
//        String body = reverseBytes(bytes);
//        return body;
        System.out.println("un.................................");
        return null;
    }
}
