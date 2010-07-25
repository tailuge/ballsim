function SpinSelector()
{ 
	var spinX = 0;
	var spinY = 0.5;
 	var canvas;
 	var ctx;
	var center;
	var scale;
	 	
	this.draw = draw;
	this.init = init;
		
	function init()
	{
        canvas = document.getElementById('spin');	
        ctx = canvas.getContext('2d');
        
        ctx.shadowOffsetX = 2;
		ctx.shadowOffsetY = 2;
		ctx.shadowBlur = 10;
		ctx.shadowColor = "grey";

   		center = canvas.width/2;
    	scale = center * 0.8;
	    canvas.onmousedown = click;
		draw();
	}
	
	function draw()
	{
                   
        /*ball*/               
    
    	ctx.clearRect(0, 0, canvas.width, canvas.height);
    	ctx.fillStyle = 'white';
    	ctx.beginPath();
    	ctx.moveTo(center + scale, center);
	    ctx.arc(center, center, scale, 0, Math.PI*2, true);
    	ctx.fill();
    	ctx.stroke();
    
	    /*tip*/
    
    	ctx.beginPath();
    	ctx.moveTo(center+spinX*scale+scale/5, center-spinY*scale);
    	ctx.fillStyle = 'blue';
    	ctx.arc(center+spinX*scale, center-spinY*scale, scale/5, 0, Math.PI*2, true);
    	ctx.fill();
    	ctx.stroke();    
	}
	
	function click(event)
	{
   		spinY = -(event.offsetY - canvas.height/2) / (canvas.height/2);
   		
   		document.onmousemove = click;
   		document.onmouseup = clearAll;
   		
   		draw();
	}

	function clearAll(event)
	{
   		document.onmousemove = null;
   		document.onmouseup = null;
	}	
}