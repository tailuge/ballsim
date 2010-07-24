function PowerSelector()
{ 
	var power = 0.5;
 	var canvas;
 	var ctx;
	var scale;
	 	
	this.draw = draw;
	this.init = init;
		
	function init()
	{
        canvas = document.getElementById('power');	
        ctx = canvas.getContext('2d');
               ctx.shadowOffsetX = 2;
		ctx.shadowOffsetY = 2;
		ctx.shadowBlur = 5;
		ctx.shadowColor = "blue";
        
    	scale = canvas.width * 0.95;
	    canvas.onmousedown = function(event)
    	{
    		power = (event.offsetX / canvas.width);
    		draw();
		}
		draw();
	}
	
	function draw()
	{                   
        /*power bar*/               
    
    	ctx.clearRect(0, 0, canvas.width, canvas.height); 
    
    	ctx.beginPath();
    	ctx.moveTo(0,0);
    	ctx.fillStyle = 'lightblue';
    	ctx.fillRect(0,0,scale,canvas.height*0.9)
    	ctx.fill();
    	ctx.fillStyle = 'purple';
    	ctx.fillRect(2,2,power * scale,canvas.height*0.75)
    	ctx.fill();
    	ctx.stroke();    
	}
}