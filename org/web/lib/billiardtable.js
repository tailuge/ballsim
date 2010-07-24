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
	    

function BilliardTable()
{ 
 	var canvas;
 	var ctx;
	var center;
	var scale;
	 	
	this.draw = draw;
	this.init = init;

    var ball1 = new Ball();
    var ball2 = new Ball();
    var ball3 = new Ball();
		
	function init()
	{
        canvas = document.getElementById('billiardtable');	
        ctx = canvas.getContext('2d');
        ctx.shadowOffsetX = 1;
		ctx.shadowOffsetY = 1;
		ctx.shadowBlur = 2;
		ctx.shadowColor = "black";
   		center = canvas.width/2;
    	scale = center * 0.8;
    	
		ball1.init(1,'white',190,290);
		ball2.init(2,'red',90,90);
		ball3.init(3,'yellow',150,370);
    	
	    canvas.onmousedown = function(event)
    	{
    		draw();
		}
		draw();
		
	}

	function draw()
	{
                   
        /*table*/               
    
    	ctx.beginPath();
    	ctx.moveTo(0,0);
    	ctx.fillStyle = 'blue';
    	ctx.fillRect(0,0,canvas.width,canvas.height)
    	ctx.fill();
    	ctx.stroke();    
    	
    	ball1.draw();
    	ball2.draw();
    	ball3.draw();
	}	
}

 