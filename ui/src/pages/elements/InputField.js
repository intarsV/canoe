import React from "react";

const InputField = ({id, label, pattern, register, errors}) => {

    return (
        <div className="span">
            <label className="marginLeftRight" htmlFor={id}>{label}:</label>
            <input name={id} type="text" autoComplete='off'
                   className={errors[id] ? "input-field-error-state" : "input-field"}
                   ref={register({required: true, pattern: pattern})}/>
           <br/>
            {errors[id]?.type === "required" &&
            <span className="error-text ">Required field!</span>}
            {errors[id]?.type === "pattern" &&
            <span className="error-text ">Invalid value!</span>}
        </div>
    )
};

export default InputField;