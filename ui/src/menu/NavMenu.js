import React, {useState} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Dropdown, DropdownMenu, DropdownToggle} from "reactstrap";

const NavMenu = () => {

    const [dropdownOpen, setDropdownOpen] = useState(false);

    const toggle = () => setDropdownOpen(prevState => !prevState);

    const userMenu = [
        {key: 'Boat Class', value: '/boat-class'},
        {key: 'Competitor', value: '/competitor'},
        {key: 'Country', value: '/country'},
        {key: 'Event', value: '/event'},
        {key: 'Event Registry', value: '/event-registry'},
        {key: 'Group', value: '/group'},
        {key: 'MCU Data', value: '/mcu-data'},
        {key: 'Race Processing', value: '/race-processing'},
        {key: 'Race Manager', value: '/raceManager'},
        {key: 'Results', value: '/results'},
        {key: 'RaceConfig', value: '/race-config'}
    ];

    return(
        <Dropdown isOpen={dropdownOpen} toggle={toggle} >
            <DropdownToggle caret className='button'>
                Menu
            </DropdownToggle>
            <DropdownMenu style={{backgroundColor: "rgba(256, 256, 256, 0.9)" }}>
                {userMenu.map((item, index) =>
                    <div key={index} onClick={toggle}><Link className="navLinks" to={item.value} >{item.key} </Link></div>)}
            </DropdownMenu>
        </Dropdown>
    )
};

export default withRouter(NavMenu);