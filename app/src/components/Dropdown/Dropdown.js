import React from 'react';

function Dropdown({title, children}) {
    const [active, setActive] = React.useState(false);
    const style = active ? {display: "block"} : {display: "none"};

    return (
        <div className="dropdown d-inline-block">
            <button 
                className="btn btn-default dropdown-toggle mx-1" 
                onClick={() => {setActive(!active)}}
            >
                {title}
            </button>
            <div 
                className="dropdown-menu" 
                style={{...style, top: "auto"}}
                onClick={() => {setActive(false)}}
            >
                {children}
            </div>
        </div>
    );
}

export default Dropdown;