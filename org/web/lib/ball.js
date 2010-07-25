function Ball()
{
    var x;
    var y;
    var angx;
    var angy;
    var colour;
    var id;
    var radius = 10;
 	var canvas;
 	var ctx;
    
    this.draw = draw;
	this.init = init;
    
	function init(id_,colour_,x_,y_)
	{
        canvas = document.getElementById('billiardtable');	
        ctx = canvas.getContext('2d');
        x = x_;
        y = y_;
        id = id_;
        colour = colour_;
	}

	function draw()
	{
		drawShadow();
		drawBall();
		drawShine();
	}
	    	
	function drawBall()
	{
    	ctx.shadowOffsetX = 0;
		ctx.shadowOffsetY = 0;
		ctx.shadowBlur = 0;
    	ctx.beginPath();
    	ctx.moveTo(x+radius, y);
	    ctx.arc(x, y, radius, 0, Math.PI*2, true);
    	ctx.fill();
    	ctx.stroke();
    	
    }	
    
   	function drawShadow()
	{
    	ctx.fillStyle = colour;
    	ctx.strokeStyle = colour;
        ctx.shadowOffsetX = 1;
		ctx.shadowOffsetY = 1;
		ctx.shadowBlur = 4;
		ctx.shadowColor = 'black';
    	ctx.beginPath();
    	ctx.moveTo(x+radius, y);
	    ctx.arc(x, y, radius, 0, Math.PI*2, true);
    	ctx.fill();
    	ctx.stroke();
	} 

   	function drawShine()
	{
    	ctx.fillStyle = 'white';
    	ctx.strokeStyle = 'white';
        ctx.shadowOffsetX = 1;
		ctx.shadowOffsetY = 1;
		ctx.shadowBlur = 5;
		ctx.shadowColor = 'white';
		
		var o = 3;
    	ctx.beginPath();
    	ctx.moveTo(x-o, y-o);
	    ctx.arc(x-o, y-o, 1, 0, Math.PI*2, true);
    	ctx.fill();
    	ctx.stroke();
	} 
}
	    