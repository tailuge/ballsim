function BilliardTable()
{ 
 	var canvas;
 	var ctx;
	var center;
	var scale;
	 	
	this.draw = draw;
	this.init = init;
		
	function init()
	{
        canvas = document.getElementById('billiardtable');	
        ctx = canvas.getContext('2d');
        ctx.shadowOffsetX = 2;
		ctx.shadowOffsetY = 2;
		ctx.shadowBlur = 10;
		ctx.shadowColor = "black";
   		center = canvas.width/2;
    	scale = center * 0.8;
	    canvas.onmousedown = function(event)
    	{
    		spinY = (event.offsetY - canvas.height/2) / (canvas.height/2);
    		draw();
		}
		draw();
	}

	function draw()
	{
                   
        /*table*/               
    
    	ctx.beginPath();
    	ctx.moveTo(0,0);
    	ctx.fillStyle = 'lightblue';
    	ctx.fillRect(0,0,canvas.width,canvas.height)
    	ctx.fill();
    	ctx.stroke();    
	}	
}

 