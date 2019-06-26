# coding=UTF-8
__author__ = 'zhaocw'

"""
创建打包信息，含有当前的git分支名称、commit id、当前时间信息
"""
import os,sys,time,socket,io

#os.chdir('/opt/Jenkins/workspaces/brm/brm-all/')
cur_path = os.getcwd() #should be the src/main/resources

file_name = "build_info.properties"

os.system('git rev-parse --abbrev-ref HEAD > build_branch.info') #get branch name
os.system('git rev-parse HEAD > build_commit_id.info') #get branch name
os.system('git log -1 --format=%cd --date=local > build_date.info') #get commit time

time.sleep(2)

the_build_time = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
the_build_branch = ''
the_build_commit_id = ''
the_build_commit_time = ''
the_build_ip = 'unknown'

import socket
try:
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.connect(("10.21.16.147", 22))
    the_build_ip = s.getsockname()[0]
    s.close()
    # if the_build_ip.endswith('%'):
    #     the_build_ip = the_build_ip[:-1]
except:
    pass

with io.open('build_branch.info',  mode='r') as myfile:
    the_build_branch=myfile.read().replace('\n', '')
with io.open('build_commit_id.info', mode='r') as myfile:
    the_build_commit_id=myfile.read().replace('\n', '')
with io.open('build_date.info', mode='r') as myfile:
    the_build_commit_time=myfile.read().replace('\n', '')


with io.open(cur_path+"/"+file_name, mode='w') as the_file:
    the_file.write('build_branch=%s\nbuild_commit_id=%s\n'
                   'build_commit_time=%s\nbuild_datetime=%s\nbuild_ip=%s\n'
                   %(the_build_branch,the_build_commit_id,
                     the_build_commit_time,the_build_time,the_build_ip))