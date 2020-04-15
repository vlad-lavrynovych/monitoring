import React, {useState} from "react";
import FilterForm from "./FilterForm";
import {useParams} from "react-router";
import CheckResultRow from "./CheckResultRow";
import LogResult from "./LogResult";

const ViewDetails = () => {
    const {id} = useParams();
    const {logResults, setLogResults} = useState([]);

    const filterRequest = (params) => {
        let url = new URL("http://localhost:8080/logs");
        Object.keys(params).forEach(key => url.searchParams.append(key, params[key]))
        fetch(url)
            .then((response) => response.json())
            .then(json => setLogResults(json))
    };

    return (
        <div>
            <div className="navbar navbar-dark bg-dark d-flex justify-content-center align-items-center">
                <h1 className="text-white">Website Monitoring Tool</h1>
            </div>
            <FilterForm id={id} filterReauest={filterRequest}/>
            <div className="d-flex justify-content-around flex-column">
                <br/>
                <br/>
                <br/>

                {logResults && logResults.length > 0 ? (
                        logResults.map(result => (
                            <table className="table table-striped table-dark">
                                <thead>
                                <tr>
                                    <th>Host</th>
                                    <th>Status</th>
                                    <th>Last check</th>
                                    <th>Duration (millis)</th>
                                    <th>Response code</th>
                                    <th>Response size (bytes)</th>
                                    <th>Details</th>
                                    <th>Monitoring status</th>
                                </tr>
                                </thead>
                                <tbody>
                                <LogResult id={result.id} key={result.id} result={result}/>
                                </tbody>
                            </table>))
                        ) : (
                            <table className="table table-striped table-dark">
                                <thead>
                                <tr>
                                    <th colSpan={8} class="text-center"> Input criteria</th>
                                </tr>
                                </thead>
                            </table>
                    )}
                <br/>
                <br/>
                <br/>
            </div>
        </div>
    );
};

export default ViewDetails;