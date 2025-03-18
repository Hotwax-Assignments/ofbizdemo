package com.company.ofbizdemo.services;
import java.util.*;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.ServiceUtil;

public class OfbizDemoServices
{
    public static final String MODULE=OfbizDemoServices.class.getName();
    public static Map<String, Object> createOfbizDemo(DispatchContext dctx, Map<String, ? extends Object> context)
    {
        Map<String, Object> result=ServiceUtil.returnSuccess();
        Delegator delegator=dctx.getDelegator();
        try
        {
            GenericValue ofbizDemo=delegator.makeValue("OfbizDemo");
            ofbizDemo.setNextSeqId();
            ofbizDemo.setNonPKFields(context);
            ofbizDemo=delegator.create(ofbizDemo);
            result.put("ofbizDemoId",ofbizDemo.getString("ofbizDemoId"));
            Debug.log("First java service for creating a OfbizDemo record. "+"Record created with id:"+ofbizDemo.getString("ofbizDemoId"));          
        }catch(GenericEntityException e)
        {
            Debug.logError(e, MODULE);
            return ServiceUtil.returnError("Error in creating record in "+MODULE);
        }
        return result;
    }
}