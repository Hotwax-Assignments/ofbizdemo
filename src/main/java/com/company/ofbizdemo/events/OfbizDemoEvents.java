package com.company.ofbizdemo.events;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;

import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;

import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;


public class OfbizDemoEvents
{
    public static final String MODULE=OfbizDemoEvents.class.getName();
    public static String createOfbizDemo(HttpServletRequest req, HttpServletResponse resp)
    {
        // Delegator delegator=(Delegator) req.getAttribute("delegator");
        GenericValue userLogin=(GenericValue) req.getAttribute("userLogin");
        LocalDispatcher dispatcher=(LocalDispatcher) req.getAttribute("dispatcher");

        String ofbizDemoTypeId=req.getParameter("ofbizDemoTypeId");
        String firstName=req.getParameter("firstName");
        String lastName=req.getParameter("lastName");

        if(UtilValidate.isEmpty(firstName) || UtilValidate.isEmpty(lastName))
        {
            String errmsg="First name and last name are required";
            req.setAttribute("_ERROR_MESSAGE_", errmsg);
            return "error";
        }

        String comments=req.getParameter("comments");

        try
        {
            Debug.logInfo("Creating OfbizDemo using groovy service", MODULE);
            dispatcher.runSync("createOfbizDemoGroovy", UtilMisc.toMap("ofbizDemoTypeId", ofbizDemoTypeId, 
                                                                        "firstName", firstName, 
                                                                       "lastName", lastName, 
                                                                       "comments", comments,
                                                                       "userLogin", userLogin));
        }catch(GenericServiceException e)
        {
            req.setAttribute("_ERROR_MESSAGE_", "Unable to create OfbizDemo entity, "+e.getMessage());
            return "error";
        }

        req.setAttribute("_EVENT_MESSAGE_", "OfbizDemo record created successfully");
        return "success";

    }
}