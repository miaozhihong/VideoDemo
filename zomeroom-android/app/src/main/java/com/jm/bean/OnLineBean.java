package com.jm.bean;

import java.util.List;

/**
 * 作者 Created by $miaozhihong on 2018/11/21 09:47
 * 页面功能:
 */
public class OnLineBean {

    /**
     * code : 0
     * list : [{"deviceToken":"Ag8TdWyLHWvwuBKuO9tw1ERSXSFRqXCn24oEAZnG4U-N","disabled":0,"email":"1","flag":null,"id":1,"isDel":0,"isRecording":1,"isStatus":0,"loginName":"admin","msg":null,"name":"admin","newTime":"0","oldTime":"0","pageNo":null,"pageSize":null,"password":"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918","qq":"0","registerTime":1525655287000,"remark":"","salt":"","sex":1,"startNo":null,"tel":"13278900987","updateTime":0},{"deviceToken":"Ajxp5_0u24-toopw1G4vgOg5kZcxWkRefSEB3wXnamjX","disabled":0,"email":"0","flag":null,"id":3,"isDel":0,"isRecording":0,"isStatus":0,"loginName":"ZOME1","msg":null,"name":"郭霞霞","newTime":"0","oldTime":"0","pageNo":null,"pageSize":null,"password":"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92","qq":"0","registerTime":1525655287000,"remark":"","salt":"","sex":1,"startNo":null,"tel":"13691571217","updateTime":0},{"deviceToken":"AtBOcxlwSWpMJwlunl9cLGUUWj-BF-oQf-4xLK6uKTLD","disabled":0,"email":"0","flag":null,"id":4,"isDel":0,"isRecording":0,"isStatus":0,"loginName":"ZOME2","msg":null,"name":"翟琳","newTime":"0","oldTime":"0","pageNo":null,"pageSize":null,"password":"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92","qq":"0","registerTime":1525655287000,"remark":"","salt":"","sex":1,"startNo":null,"tel":"15101107960","updateTime":0},{"deviceToken":"Av77yYvq_iIRbidYC4WFLv7tmdK4iU1U0zPqsPH3MqNE","disabled":0,"email":"0","flag":null,"id":6,"isDel":0,"isRecording":0,"isStatus":0,"loginName":"ZOME4","msg":null,"name":"苗志芳","newTime":"0","oldTime":"0","pageNo":null,"pageSize":null,"password":"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92","qq":"0","registerTime":1525655287000,"remark":"","salt":"","sex":1,"startNo":null,"tel":"13522330540","updateTime":0},{"deviceToken":"Ah3_kcdnXbr9bNeuVYiqaJEZmzN6sxI_DjKROLqokSYZ","disabled":0,"email":"0","flag":null,"id":7,"isDel":0,"isRecording":0,"isStatus":1,"loginName":"ZOME5","msg":null,"name":"时爽","newTime":"0","oldTime":"0","pageNo":null,"pageSize":null,"password":"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92","qq":"0","registerTime":1525655287000,"remark":"","salt":"","sex":1,"startNo":null,"tel":"15911095671","updateTime":0},{"deviceToken":"AsIJHuMfott58LrWaplVNZXe1K0h0MYNbOxwi_VQrGvT","disabled":0,"email":"0","flag":null,"id":8,"isDel":0,"isRecording":0,"isStatus":0,"loginName":"ZOME6","msg":null,"name":"景霞","newTime":"0","oldTime":"0","pageNo":null,"pageSize":null,"password":"f707fdda7c874ff49ebfb2c88a2860c5ff4ce3d94a21efb76566ad0f92c9ad57","qq":"0","registerTime":1525655287000,"remark":"","salt":"","sex":1,"startNo":null,"tel":"18810962543","updateTime":0},{"deviceToken":"AnVW83odSO2nKjFi4hhLtoIyDNq_SKjBxFyP3pYrlhQq","disabled":0,"email":"0","flag":null,"id":10,"isDel":0,"isRecording":0,"isStatus":0,"loginName":"ZOME7","msg":null,"name":"候飞龙","newTime":"0","oldTime":"0","pageNo":null,"pageSize":null,"password":"2680d8152dabba84c80a50e98afbcd84885b01a67ae276fdb1807fb3cfa66abb","qq":"ss","registerTime":1527997046884,"remark":"","salt":"","sex":1,"startNo":null,"tel":"18301108917","updateTime":0},{"deviceToken":"Aj2t-48LjlQARhX4SqgxzugAX8pJZWqUI9x6cnBU9JQ1","disabled":0,"email":"0","flag":null,"id":12,"isDel":0,"isRecording":0,"isStatus":0,"loginName":"ZOME8","msg":null,"name":"候飞龙","newTime":"0","oldTime":"0","pageNo":null,"pageSize":null,"password":"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92","qq":"12","registerTime":1532101758701,"remark":"","salt":"","sex":1,"startNo":null,"tel":"15711015080","updateTime":0},{"deviceToken":"AifLqz_220X-BxkZpg_y0cZTFEPxS2ITEwedODT_ARFG","disabled":0,"email":"0","flag":null,"id":13,"isDel":0,"isRecording":0,"isStatus":1,"loginName":"ZOME9","msg":null,"name":"薛俊峰","newTime":"0","oldTime":"0","pageNo":null,"pageSize":null,"password":"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92","qq":"0","registerTime":1538371660693,"remark":"","salt":"","sex":1,"startNo":null,"tel":"13691380098","updateTime":0},{"deviceToken":"Aon83Y5IgHHTT9iBtYQ6CfZBXPzrITEixGkuDeQfq6O6","disabled":0,"email":"1","flag":null,"id":14,"isDel":0,"isRecording":0,"isStatus":1,"loginName":"ZOME10","msg":null,"name":"魏玉玲","newTime":"0","oldTime":"0","pageNo":null,"pageSize":null,"password":"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92","qq":"1","registerTime":1540386189625,"remark":"","salt":"","sex":0,"startNo":null,"tel":"18201188930","updateTime":0},{"deviceToken":"Aj1Tg_5vWMhAXMrJcMMTI8jK8TKWV2D_G-rtm_Uhmife","disabled":0,"email":"0","flag":null,"id":15,"isDel":0,"isRecording":0,"isStatus":0,"loginName":"ZOME3","msg":null,"name":"冯明明","newTime":"0","oldTime":"0","pageNo":null,"pageSize":null,"password":"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92","qq":"0","registerTime":1540386189625,"remark":"","salt":"","sex":1,"startNo":null,"tel":"13611227120","updateTime":0}]
     */

    private int code;
    private List<ListBean> list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }
}
