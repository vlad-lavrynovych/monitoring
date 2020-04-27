import React, {useState} from "react";


const AddingForm = props => {

    const initialState = {
        url: "",
        queryingInterval: 10000,
        responseTimeOk: 20000,
        responseTimeCritical: 10000,
        responseTimeWarning: 50000,
        expectedHttpResponseCode: 200,
        minExpectedResponseSize: 0,
        maxExpectedResponseSize: 100000,
        monitored: true
    };

    const [state, setState] = useState(initialState);

    const changeHandler = (event) => {
        event.persist();
        console.log(event);
        setState(inputs => ({...inputs, [event.target.name]: event.target.value}))
    };

    const handleSubmit = (event) => {
        if (state.url === "") {
            alert("ERROR :: URL IS EMPTY")
            return;
        }
        if (event) {
            event.preventDefault();
            props.addConfig(state);
            setState(initialState);
        }
    };

    return (
        <form>
            <div className="form-group d-flex flex-md-column justify-content-center align-items-center">
                <label className="w-50">url</label>
                <input onChange={changeHandler} className="form-control w-50" required placeholder="http://example.com/"
                       type="text" value={state.url}
                       name="url"/>
                <label className="w-50">querying interval (millis)</label>
                <input onChange={changeHandler} className="form-control w-50" type="number"
                       value={state.queryingInterval} name="queryingInterval"/>
                <label className="w-50">response time ok (millis)</label>
                <input onChange={changeHandler} className="form-control w-50" type="number" value={state.responseTimeOk}
                       min="0:00:00"
                       name="responseTimeOk"/>
                <label className="w-50">response time warning (millis)</label>
                <input onChange={changeHandler} className="form-control w-50" type="number"
                       value={state.responseTimeWarning} min="0:00:00"
                       name="responseTimeWarning"/>
                <label className="w-50">response time critical (millis)</label>
                <input onChange={changeHandler} className="form-control w-50" type="number"
                       value={state.responseTimeCritical} min="0:00:00"
                       name="responseTimeCritical"/>
                <label className="w-50">expected http code</label>
                <input onChange={changeHandler} className="form-control w-50" value={state.expectedHttpResponseCode}
                       type="number"
                       name="expectedHttpResponseCode"/>
                <label className="w-50">min expected response size (bytes)</label>
                <input onChange={changeHandler} className="form-control w-50" required type="number"
                       value={state.minExpectedResponseSize} name="minExpectedResponseSize"/>
                <label className="w-50">max expected response size (bytes)</label>
                <input onChange={changeHandler} className="form-control w-50" required type="number"
                       value={state.maxExpectedResponseSize} name="maxExpectedResponseSize"/>
                <br/>
                <button className="button btn-success btn-lg" type="submit" onClick={handleSubmit}>Add web-site</button>
            </div>
        </form>
    )
};
export default AddingForm;
