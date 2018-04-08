pragma solidity ^0.4.0;
contract Record {

    uint timestamp;
    uint rpm;
    uint rot;
    uint spd;

    function Record(uint _timestamp, uint _rpm, uint _rot, uint _spd) public {
        timestamp = _timestamp;
        rpm = _rpm;
        rot = _rot;
        spd = _spd;
    }
    
    function getTimestamp() public constant returns (uint _timestamp) {
        _timestamp = timestamp;
    }
    
    function getRpm() public constant returns (uint _rpm) {
        _rpm = rpm;
    }
    
    function getRot() public constant returns (uint _rot) {
        _rot = rot;
    }
    
    function getSpd() public constant returns (uint _spd) {
        _spd = spd;
    }
}
