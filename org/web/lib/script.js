window.onload = main;

var spinSelectorInstance = new SpinSelector();
var powerSelectorInstance = new PowerSelector();
var billiardTableInstance = new BilliardTable();



function main()
{
	spinSelectorInstance.init();
	powerSelectorInstance.init();
	billiardTableInstance.init();   
}