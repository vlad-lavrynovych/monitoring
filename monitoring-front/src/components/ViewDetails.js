import React, {useState} from "react";
import FilterForm from "./FilterForm";
import LineGraph from 'react-line-graph'
import LogResult from "./LogResult";

const ViewDetails = (props) => {
    const [logResults, setLogResults] = useState([]);
    let data = [];
    const props1 = {
        data,
        gridX: true,
        gridY: true,
        debug: true,
        // width: 1200,
        // height: 300,
        smoothing: 0.3,
        accent: 'palevioletred',
        fillBelow: 'rgb(255,0,12)',
        hover: true,
    };
    let labels = [1, 2, 3, 4, 5, 6, 7, 8, 9];
    const options = {fillColor: '#FFFFFF', strokeColor: '#0000FF'};
    const filterRequest = params => {
        data = [];
        let url = new URL("http://localhost:8081/logs");
        Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));
        fetch(url)
            .then((response) => response.json())
            .then(json => setLogResults(json));
        data = logResults.map(s => s.duration);

    };

    for (let i = 0; i < logResults.length; i++) {
        let log = logResults[i];
        data.push([i, log.duration]);
    }

    return (
        <div>
            <div className="navbar navbar-dark bg-dark d-flex justify-content-center align-items-center">
                <h1 className="text-white">Website Monitoring Tool</h1>
            </div>
            <FilterForm id={props.match.params.id} filterRequest={filterRequest}/>
            <div className="d-flex justify-content-around flex-column align-items-center">
                <br/>
                <br/>
                <br/>
                <div  className="w-75 h-50">
                    <LineGraph {...props1}/>
                </div>
                <table className="table table-striped table-dark w-75">
                    <thead>
                    <tr>
                        {/*<th>Host</th>*/}
                        <th>Status</th>
                        <th>Last check</th>
                        <th>Duration (millis)</th>
                        <th>Response code</th>
                        <th>Response size (bytes)</th>
                        <th>Details</th>
                        {/*<th>Monitoring status</th>*/}
                    </tr>
                    </thead>
                    <tbody>
                    {logResults.length > 0 ? (
                        logResults.map(result => (
                            <LogResult id={result.id} key={result.id} result={result}/>
                        ))) : (
                        <tr>
                            <th colSpan={8} className="text-center"> Input criteria</th>
                        </tr>
                    )}
                    </tbody>
                </table>
                <br/>
                <br/>
                <br/>
            </div>
        </div>
    );
};

export default ViewDetails;