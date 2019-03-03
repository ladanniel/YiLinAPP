<style>
	.staffinfo:after {
		    background-color: #F5F5F5;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px 0 4px 0;
		    color: #9DA0A4;
		    content: "用户详细信息";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 3px 7px;
		    position: absolute;
		    top: -1px;
		}
		.staffinfo {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		}
</style>
<div style="" class="staffinfo ui-sortable"> 
	<div class="row">
		<div class="col-sm-4" style="padding-left: 3%;">
		  <p><i class="fa green fa-user"  ></i><strong>姓名：</strong><span>${staff.name}</span></p>
		  <p><i class="fa green fa-crop"></i><strong>年龄：</strong><span>${staff.age}岁</span></p>
		  <p><i class="fa green fa-calculator"></i><strong>出生日期：</strong><span>${staff.birthday?string("yyyy年MM月dd")}</span></p>
		  <p><i class="fa green fa-venus-double"></i><strong>性别：</strong><span>${(staff.sex=='male')?string('男','女')}</span></p>
	    </div>
		<div class="col-sm-4" style="padding-left: 3%;">
		  <p><i class="fa green fa-users"></i><strong>民族：</strong><span>${staff.nation}</span></p>
		  <p><i class="fa green fa-globe"></i><strong>籍贯：</strong><span>${staff.origin}</span></p>
		  <p><i class="fa green fa-cube"></i><strong>学历：</strong><span>${staff.education.name}</span></p>
		  <p><i class="fa green fa-pencil"></i><strong>专业：</strong><span>${staff.major}</span></p>
	    </div>
		<div class="col-sm-4" style="padding-left: 3%;">
		  <p><i class="fa green  fa-graduation-cap"></i><strong>毕业院校：</strong><span>${staff.graduate_school}</span></p>
		  <p><i class="fa green fa-envelope"></i><strong>电子邮箱：</strong><span>${staff.email}</span></p>
		  <p><i class="fa green fa-user"></i><strong>用户账号：</strong><span>${staff.account.account}</span></p>
		  <p><i class="fa green fa-level-up"></i><strong> 所属商户：</strong><span>${staff.account.company.name}</span></p>
	    </div>
	</div>
</div>