function Cue()
{
    var x;
    var y;
    var angle= 1.7;
    var radius = 10;
 	var canvas;
 	var ctx;
    
    this.draw = draw;
	this.init = init;
    
	function init(x_,y_)
	{
        canvas = document.getElementById('billiardtable');	
        ctx = canvas.getContext('2d');
        x = x_;
        y = y_;
        canvas.onmousedown = click;
	}

	function draw()
	{
		drawCue();
	}
	    	
	function drawCue()
	{
		ctx.save();

		ctx.translate(x,y);	
		ctx.rotate(angle);	
    	ctx.beginPath();
    	ctx.lineWidth = 3;
    	ctx.strokeStyle = '#8A4B08';
    	ctx.shadowOffsetX = 2;
		ctx.shadowOffsetY = 2;
		ctx.shadowBlur = 6;
		ctx.shadowColor = 'black';
    	ctx.moveTo(radius + 3, 0.5);
	    ctx.lineTo(300,2);
    	ctx.moveTo(radius + 3, -0.5);
	    ctx.lineTo(300,-2);
    	ctx.fill();
    	ctx.stroke();
    	
    	ctx.restore();
    }	
    
    function click(event)
	{
   		var dx = event.offsetX - x;
   		var dy = event.offsetY - y;
   		
   		angle = Math.atan(dy/dx);

		if (dx<0)
		   angle += Math.PI
		   
   		billiardTableInstance.draw();
   		
   		document.onmousemove = click;
   		document.onmouseup = clearAll;   		
	}

	function clearAll(event)
	{
   		document.onmousemove = null;
   		document.onmouseup = null;
	}	
}