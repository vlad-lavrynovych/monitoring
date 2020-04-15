import React from "react";
import {Link} from "react-router-dom";
import AddingForm from "./AddingForm";
import {useHistory} from "react-router";


const LogResult = (props) => {

    const getStatusColor = () => {
        if (
            props.result.status === "OK"
        ) return "bg-success";
        if (
            props.result.status === "WARNING"
        ) return "bg-warning";
        if (
            props.result.status === "CRITICAL"
        ) return "bg-danger"
    };

    return (
        <tr>
            <th>{props.result.config.url}</th>
            <th className={getStatusColor()}>{props.result.status} </th>
            <th>{new Date(props.result.lastCheck).toLocaleString()}</th>
            <th>{props.result.duration}</th>
            <th>{props.result.responseCode}</th>
            <th>{props.result.responseSize}</th>
            <th>{props.result.details}</th>
            <th>{props.result.config.monitored ? "Monitored" : " Not Monitored"}</th>
        </tr>
    );
};

export default LogResult;
