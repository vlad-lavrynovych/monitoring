import React from "react";
import '../css/bootstrap.css'

const CheckResultRow = (props) => {

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
            <th>
                <div>
                    <button type="submit" className="btn btn-primary" onClick={() => {
                        props.getFullInfo(props.id)
                    }}>Details
                    </button>
                </div>
            </th>
            <th>
                <div>
                    <button type="submit" className="btn btn-danger" onClick={() => {
                        props.deleteResult(props.id)
                    }}>Delete
                    </button>
                </div>
            </th>
            <th>
                <div>
                    <button type="submit" className="btn btn-warning" onClick={() => {
                        props.changeMonitoringStatus(props.id)
                    }}>
                        {props.result.config.monitored ? "Disable" : " Enable"} </button>
                </div>
            </th>
        </tr>
    );
};

export default CheckResultRow;
